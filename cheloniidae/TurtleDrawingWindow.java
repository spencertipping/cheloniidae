// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae;

import java.awt.image.BufferedImage;
import java.awt.event.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.util.List;
import java.util.ArrayList;

public class TurtleDrawingWindow extends Frame implements TurtleViewport {
  public static final int      DRAWING_REFRESH_PER_LINES   = 10000;
  public static final int      INTERMEDIATE_RENDER_CUTOFF  = 10000;
  public static final int      LINE_POINT_MAXIMUM_DISTANCE = 16;

  protected BufferedImage      turtleOutput                = null;
  protected BufferedImage      turtleLayer                 = null;
  protected List<LineProvider> providers                   = new ArrayList<LineProvider> ();
  protected List<Turtle>       visibleTurtles              = new ArrayList<Turtle> ();

  protected Vector             virtualPOV                  = new Vector (0, 0, -100.0);
  protected Vector             virtualPOVUp                = new Vector (0, 1, 0);
  protected Vector             virtualPOVForward           = new Vector (0, 0, 1);
  protected Vector             virtualPOVLeft              = new Vector (-1, 0, 0);

  protected int                mouseDownX                  = 0;
  protected int                mouseDownY                  = 0;
  protected boolean            mouseDown                   = false;
  protected Thread             graphicsRequestRunner       = null;
  protected boolean            graphicsRequestCancelFlag   = false;
  protected boolean            fisheye3D                   = false;
  
  public TurtleDrawingWindow () {}

  protected void regenerateImages () {
    turtleOutput = new BufferedImage (super.getWidth (), super.getHeight (), BufferedImage.TYPE_3BYTE_BGR);
    turtleLayer  = new BufferedImage (super.getWidth (), super.getHeight (), BufferedImage.TYPE_3BYTE_BGR);
  }

  protected void handleResize () {
    regenerateImages ();
    enqueueGraphicsRefreshRequest (true);
  }

  protected void initialize () {
    super.addWindowListener      (new WindowListener      () {public void windowClosing     (WindowEvent e)    {dispose ();}
                                                              public void windowActivated   (WindowEvent e)    {}
                                                              public void windowClosed      (WindowEvent e)    {}
                                                              public void windowDeactivated (WindowEvent e)    {}
                                                              public void windowDeiconified (WindowEvent e)    {}
                                                              public void windowIconified   (WindowEvent e)    {}
                                                              public void windowOpened      (WindowEvent e)    {}});

    super.addComponentListener   (new ComponentListener   () {public void componentResized  (ComponentEvent e) {handleResize ();}
                                                              public void componentMoved    (ComponentEvent e) {}
                                                              public void componentHidden   (ComponentEvent e) {}
                                                              public void componentShown    (ComponentEvent e) {}});

    super.addMouseListener       (new MouseListener       () {public void mouseReleased     (MouseEvent e)     {mouseDown = false;
                                                                                                                for (LineProvider p : providers) p.sort (virtualPOV);
                                                                                                                enqueueGraphicsRefreshRequest (true);}
                                                              public void mousePressed      (MouseEvent e)     {mouseDown = true;
                                                                                                                mouseDownX = e.getX ();
                                                                                                                mouseDownY = e.getY ();}
                                                              public void mouseClicked      (MouseEvent e)     {}
                                                              public void mouseEntered      (MouseEvent e)     {}
                                                              public void mouseExited       (MouseEvent e)     {}});

    super.addMouseMotionListener (new MouseMotionListener () {public void mouseDragged (MouseEvent e) {
                                                                if (mouseDown) {
                                                                  // A normal drag translates the view locally.
                                                                  if (! e.isShiftDown () && ! e.isControlDown ())
                                                                    virtualPOV.add (new Vector (virtualPOVUp).multiply (e.getY () - mouseDownY).addScaled (virtualPOVLeft, e.getX () - mouseDownX));
                                                                  else if (e.isShiftDown ()) {
                                                                    virtualPOVLeft    = virtualPOVLeft.rotateAbout    (virtualPOVUp,   e.getX () - mouseDownX).normalize ();
                                                                    virtualPOVForward = virtualPOVForward.rotateAbout (virtualPOVUp,   e.getX () - mouseDownX);
                                                                    virtualPOVUp      = virtualPOVUp.rotateAbout      (virtualPOVLeft, e.getY () - mouseDownY).normalize ();
                                                                    virtualPOVForward = virtualPOVForward.rotateAbout (virtualPOVLeft, e.getY () - mouseDownY).normalize ();
                                                                  }

                                                                  mouseDownX = e.getX ();
                                                                  mouseDownY = e.getY ();

                                                                  enqueueGraphicsRefreshRequest (false);
                                                                }
                                                              }
                                                              public void mouseMoved (MouseEvent e) {}});

    super.addMouseWheelListener  (new MouseWheelListener  () {public void mouseWheelMoved (MouseWheelEvent e) {
                                                                virtualPOV.addScaled (virtualPOVForward, e.getScrollAmount ());
                                                                enqueueGraphicsRefreshRequest (false);
                                                              }});

    super.setSize       (600, 372);
    super.setTitle      ("Cheloniidae");
    super.setVisible    (true);
    super.setBackground (Color.WHITE);

    handleResize ();
  }

  public void update (Graphics g) {paint (g);}
  public void paint  (Graphics g) {g.drawImage (turtleLayer, 0, 0, null);}

  public void enqueueGraphicsRefreshRequest (boolean antialiased) {
    if (turtleOutput != null && turtleLayer != null) {
      if (graphicsRequestRunner != null && graphicsRequestRunner.isAlive ()) {
        graphicsRequestCancelFlag = true;
        try {graphicsRequestRunner.join ();}
        catch (InterruptedException e) {}
      }

      if (antialiased) {
        graphicsRequestCancelFlag = false;
        (graphicsRequestRunner = new Thread (new Runnable () {
          public void run () {
            drawLines (true);
            drawTurtles (true);
            repaint ();
          }
        })).start ();
      } else {
        drawLines (false);
        drawTurtles (false);
        repaint ();
      }
    }
  }

  public synchronized Vector transformPoint (Vector v)
    {return new Vector (v).subtract (virtualPOV).toCoordinateSpace (virtualPOVLeft, virtualPOVUp, virtualPOVForward);}

  public synchronized Vector projectPoint (Vector v)
    {return fisheye3D ? new Vector (v).normalize () : new Vector (v).divide (v.z);}

  public TurtleDrawingWindow add (Turtle t) {
    visibleTurtles.add (t);
    providers.add (t.lineProvider ());
    return this;
  }

  protected void renderLine (BufferedImage target, Color c, int x1, int y1, int x2, int y2) {
    // This method renders a single line sloppily onto an image.
    // Basically, it keeps filling in points until no two points
    // are more than 4 pixels apart. It uses binary splitting.

    if ((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2) > LINE_POINT_MAXIMUM_DISTANCE) {
      renderLine (target, c, x1, y1, (x1 + x2) >> 1, (y1 + y2) >> 1);
      renderLine (target, c, (x1 + x2) >> 1, (y1 + y2) >> 1, x2, y2);
    } else
      target.setRGB ((x1 + x2) >> 1, (y1 + y2) >> 1, c.getRGB ());
  }

  protected void renderLine (Line l, Graphics2D g, BufferedImage target, boolean antialiasing) {
    // This method renders a single line onto the screen.
    // I'm assuming that the graphics context has already been
    // initialized as far as antialiasing is concerned.

    Vector v1 = transformPoint (l.v1);
    Vector v2 = transformPoint (l.v2);

    // If either point is behind the camera (but not both), then solve
    // for the point at Z = 1. We then use that point for the end of the
    // line.
    if (v1.z < 0.0 && v2.z < 0.0) return;
    else if (v1.z < 0.0 || v2.z < 0.0) {
      double difference_in_z = v1.z - v2.z;             // Must be nonzero.

      if (v1.z < 0.0) {
        double factor = Math.abs ((1.0 - v1.z) / difference_in_z);
        v1.multiply (1.0 - factor).addScaled (v2, factor);
      } else {
        double factor = Math.abs ((1.0 - v2.z) / difference_in_z);
        v2.multiply (1.0 - factor).addScaled (v1, factor);
      }
    }

    double thickness = (v1.z + v2.z) / 2.0;

    if (antialiasing) {
      // Do nice rendering with strokes and such.
      g.setStroke (new BasicStroke ((float) Math.abs (l.width * thickness)));
      g.setColor  (l.color);
      g.drawLine  ((int) v1.x, (int) v1.y, (int) v2.x, (int) v2.y);
    } else
      // Do a sloppy line.
      //
      // The check for in-boundedness is here for optimization reasons. If
      // we put it at the lowest level pixel-render, then every pixel would
      // have potentially four comparisons added to it. But here, we can get
      // away with eight for an entire line.

      if (v1.x > 0.0 && v1.x < target.getWidth () && v1.y > 0.0 && v1.y < target.getHeight () &&
          v2.x > 0.0 && v2.x < target.getWidth () && v2.y > 0.0 && v2.y < target.getHeight ())
        renderLine (target, l.color, (int) v1.x, (int) v1.y, (int) v2.x, (int) v2.y);
  }

  protected void initializeAntialiasing (Graphics2D g) {
    // Due to the architecture of rendering hints, we must first
    // load them into a variable, then change them, and finally
    // put them back into the graphics context in order to commit
    // changes. Otherwise, this would be a one-liner.

    RenderingHints rh = g.getRenderingHints ();
    rh.put (rh.KEY_ANTIALIASING, rh.VALUE_ANTIALIAS_ON);
    g.setRenderingHints (rh);
  }

  protected void drawTurtles (boolean antialiased) {
    final Graphics2D g = (Graphics2D) turtleLayer.getGraphics ();
    if (antialiased) initializeAntialiasing (g);
    g.drawImage (turtleOutput, 0, 0, null);
    for (Turtle t : visibleTurtles) t.render (g, this);
  }

  protected void drawLines (boolean antialiased) {
    final Graphics2D g = (Graphics2D) turtleOutput.getGraphics ();
    if (antialiased) initializeAntialiasing (g);

    int lineCount  = 0;
    int totalLines = 0;

    for (LineProvider p : providers) totalLines += p.size ();

    if (antialiased) {
      // Line ordering matters. Merge the arrays as they're rendered.
      int[] maximumIndices = new int[providers.size ()];

      for (int i = 0; i < providers.size (); ++i) maximumIndices[i] = providers.get (i).size () - 1;

      while (lineCount < totalLines) {
        // Find the next line of maximum distance.
        int    maximumIndex = 0;
        double nextMaximum  = 0.0;

        for (int i = 0; i < providers.size (); ++i)
          if (maximumIndices[i] >= 0 && providers.get (i).get (maximumIndices[i]).cachedDistance > providers.get (maximumIndex).get (maximumIndices[maximumIndex]).cachedDistance) {
            nextMaximum  = providers.get (maximumIndex).get (maximumIndices[maximumIndex]).cachedDistance;
            maximumIndex = i;
          }

        while (maximumIndices[maximumIndex] >= 0 && providers.get (maximumIndex).get (maximumIndices[maximumIndex]).cachedDistance > nextMaximum) {
          renderLine (providers.get (maximumIndex).get (maximumIndices[maximumIndex]), g, turtleOutput, true);
          --maximumIndices[maximumIndex];
          ++lineCount;
        }
      }
    } else {
      final int lineSkip = totalLines / INTERMEDIATE_RENDER_CUTOFF;
      for (LineProvider p : providers)
        for (int i = 0; ! graphicsRequestCancelFlag && i < p.size (); i += lineSkip)
          renderLine (p.get (i), g, turtleOutput, false);
    }
  }
}
