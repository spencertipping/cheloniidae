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

public class TurtleWindow extends Frame implements Viewport {
  protected int           drawingRefreshInterval       = 10000;
  protected int           maximumLinesPerPartialRedraw = 10000;
  protected int           maximumFalseLineGranularity  = 16;

  protected BufferedImage offscreen                    = null;
  protected List<Turtle>  visibleTurtles               = new ArrayList<Turtle> ();

  protected Vector        virtualPOV                   = new Vector (0, 0, -500.0);
  protected Vector        virtualPOVUp                 = new Vector (0, 1, 0);
  protected Vector        virtualPOVForward            = new Vector (0, 0, 1);

  protected Vector        minimumExtent                = new Vector (0, 0, 0);
  protected Vector        maximumExtent                = new Vector (0, 0, 0);

  protected int           mouseDownX                   = 0;
  protected int           mouseDownY                   = 0;
  protected boolean       mouseDown                    = false;
  protected Thread        graphicsRequestRunner        = null;
  protected boolean       graphicsRequestCancelFlag    = false;
  protected boolean       fisheye3D                    = false;
  
  public TurtleWindow () {initialize ();}

  protected void handleResize () {
    offscreen = new BufferedImage (super.getWidth (), super.getHeight (), BufferedImage.TYPE_3BYTE_BGR);
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
                                                                                                                enqueueGraphicsRefreshRequest (true);}
                                                              public void mousePressed      (MouseEvent e)     {mouseDown = true;
                                                                                                                mouseDownX = e.getX ();
                                                                                                                mouseDownY = e.getY ();}
                                                              public void mouseClicked      (MouseEvent e)     {}
                                                              public void mouseEntered      (MouseEvent e)     {}
                                                              public void mouseExited       (MouseEvent e)     {}});

    super.addMouseMotionListener (new MouseMotionListener () {public void mouseDragged (MouseEvent e) {
                                  if (mouseDown) {
                                    final Vector virtualPOVRight = virtualPOVForward.cross (virtualPOVUp);
                                    final Vector center          = new Vector (maximumExtent).multiply (0.5).addScaled (minimumExtent, 0.5);
                                    final double factor          = e.isControlDown () ? 0.1 : 1.0;

                                    // A normal drag translates the view locally.
                                    if (! e.isShiftDown ())
                                      virtualPOV.addScaled (virtualPOVUp, factor * (mouseDownY - e.getY ())).addScaled (virtualPOVRight, factor * (e.getX () - mouseDownX));
                                    else {
                                      final double pitchAngle = (e.getY () - mouseDownY) * factor;
                                      final double turnAngle  = (e.getX () - mouseDownX) * factor;
                                      virtualPOV = virtualPOV.subtract (center).rotateAbout (virtualPOVUp,    turnAngle).
                                                                                rotateAbout (virtualPOVRight, pitchAngle).add (center);

                                      virtualPOVForward = virtualPOVForward.rotateAbout (virtualPOVUp,    turnAngle).
                                                                            rotateAbout (virtualPOVRight, pitchAngle).normalize ();
                                      virtualPOVUp      = virtualPOVUp.rotateAbout      (virtualPOVRight, pitchAngle).normalize ();
                                    }

                                    mouseDownX = e.getX ();
                                    mouseDownY = e.getY ();
                                    enqueueGraphicsRefreshRequest (false);
                                  }
                                                              }
                                                              public void mouseMoved (MouseEvent e) {}});

    super.addMouseWheelListener  (new MouseWheelListener  () {public void mouseWheelMoved (MouseWheelEvent e) {
                                                                virtualPOV.addScaled (virtualPOVForward, (e.isControlDown () ? -1 : -10) * e.getWheelRotation ());
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

  public void enqueueGraphicsRefreshRequest (final boolean antialiased) {
    if (turtleOutput != null && turtleLayer != null) {
      if (graphicsRequestRunner != null && graphicsRequestRunner.isAlive ()) {
        graphicsRequestCancelFlag = true;
        try {graphicsRequestRunner.join ();}
        catch (InterruptedException e) {}
      }

      graphicsRequestCancelFlag = false;
      (graphicsRequestRunner = new Thread (new Runnable () {
        public void run () {
          
          repaint ();
        }
      })).start ();
    }
  }

  public Vector transformPoint (final Vector v)
    {return new Vector (v).subtract (virtualPOV).toCoordinateSpace (virtualPOVUp.cross (virtualPOVForward), virtualPOVUp, virtualPOVForward);}

  public Vector projectPoint (final Vector v)
    {return (fisheye3D ? new Vector (v).normalize () : new Vector (v).divide (v.z)).multiply (super.getHeight ()).add (new Vector (super.getWidth () >> 1, super.getHeight () >> 1, 0));}

  public TurtleWindow add (final Turtle t) {
    visibleTurtles.add (t);
    return this;
  }

  public TurtleWindow pause (long milliseconds) {
    enqueueGraphicsRefreshRequest (true);
    try {Thread.sleep (milliseconds);}
    catch (InterruptedException e) {}
    return this;
  }

  protected Graphics2D context () {
    final Graphics2D     g  = offscreen.getGraphics ();
    final RenderingHints rh = g.getRenderingHints ();
    rh.put (rh.KEY_ANTIALIASING, rh.VALUE_ANTIALIAS_ON);
    g.setRenderingHints (rh);
    return g;
  }
}
