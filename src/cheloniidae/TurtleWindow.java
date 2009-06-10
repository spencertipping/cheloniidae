// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae;

import java.awt.image.BufferedImage;
import java.awt.event.*;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.util.SortedSet;
import java.util.Iterator;

public class TurtleWindow<T extends Turtle> extends Frame implements Viewport {
  public class RenderOperation extends Thread {
    protected Turtle   turtle;
    protected Viewport viewport;
    protected boolean  cancelFlag;

    public RenderOperation (Turtle _turtle, Viewport _viewport)
      {turtle = _turtle; viewport = _viewport;}

    public void cancel () {cancelFlag = true;}

    public void run () {
      cancelFlag = false;

      int linesDrawnSoFar = 0;

      Graphics2D c = viewport.context ();
      c.setColor (getBackground ());
      c.fillRect (0, 0, getWidth (), getHeight ());

      SortedSet<RenderAction> actions        = turtle.actions (viewport);
      Iterator<RenderAction>  actionIterator = actions.iterator ();

      while (! cancelFlag && actionIterator.hasNext ()) {
        actionIterator.next ().render (viewport);
        if (++linesDrawnSoFar % drawingRefreshInterval == 0) repaint ();
      }

      repaint ();
    }
  }

  protected int             drawingRefreshInterval       = 10000;

  protected BufferedImage   offscreen                    = null;
  protected Graphics2D      cachedContext                = null;
  protected TurtleGroup<T>  turtles                      = new TurtleGroup<T> ();

  protected Vector          virtualPOV                   = new Vector (0, 0, -500.0);
  protected Vector          virtualPOVUp                 = new Vector (0, 1, 0);
  protected Vector          virtualPOVForward            = new Vector (0, 0, 1);

  protected Vector          minimumExtent                = new Vector (0, 0, 0);
  protected Vector          maximumExtent                = new Vector (0, 0, 0);

  protected int             mouseDownX                   = 0;
  protected int             mouseDownY                   = 0;
  protected boolean         mouseDown                    = false;
  protected RenderOperation graphicsRequestRunner        = null;
  protected boolean         graphicsRequestCancelFlag    = false;
  protected boolean         fisheye3D                    = false;
  
  public TurtleWindow () {initialize ();}

  protected void handleResize () {
    offscreen = new BufferedImage (super.getWidth (), super.getHeight (), BufferedImage.TYPE_3BYTE_BGR);
    cachedContext = null;
    enqueueGraphicsRefreshRequest ();
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
                                                                                                                enqueueGraphicsRefreshRequest ();}
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
                                    enqueueGraphicsRefreshRequest ();
                                  }
                                                              }
                                                              public void mouseMoved (MouseEvent e) {}});

    super.addMouseWheelListener  (new MouseWheelListener  () {public void mouseWheelMoved (MouseWheelEvent e) {
                                                                virtualPOV.addScaled (virtualPOVForward, (e.isControlDown () ? -1 : -10) * e.getWheelRotation ());
                                                                enqueueGraphicsRefreshRequest ();
                                                              }});

    super.setSize       (600, 372);
    super.setTitle      ("Cheloniidae");
    super.setVisible    (true);
    super.setBackground (Color.WHITE);

    handleResize ();
  }

  public void update (Graphics g) {paint (g);}
  public void paint  (Graphics g) {g.drawImage (offscreen, 0, 0, null);}

  public TurtleWindow add (final T t) {turtles.turtles ().add (t); return this;}

  public void enqueueGraphicsRefreshRequest () {
    if (offscreen != null) {
      if (graphicsRequestRunner != null && graphicsRequestRunner.isAlive ()) {
        graphicsRequestRunner.cancel ();
        try {graphicsRequestRunner.join ();}
        catch (InterruptedException e) {}
      }

      (graphicsRequestRunner = new RenderOperation (turtles, this)).start ();
    }
  }

  public TurtleWindow pause (long milliseconds) {
    enqueueGraphicsRefreshRequest ();
    try {Thread.sleep (milliseconds);}
    catch (InterruptedException e) {}
    return this;
  }

  public Vector transformPoint (final Vector v)
    {return new Vector (v).subtract (virtualPOV).toCoordinateSpace (virtualPOVUp.cross (virtualPOVForward), virtualPOVUp, virtualPOVForward);}

  public Vector projectPoint (final Vector v)
    {return (fisheye3D ? new Vector (v).normalize () : new Vector (v).divide (v.z)).multiply (super.getHeight ()).add (new Vector (super.getWidth () >> 1, super.getHeight () >> 1, 0));}

  public Graphics2D context () {
    if (cachedContext != null) return cachedContext;
    else {
      final Graphics2D     g  = (Graphics2D) offscreen.getGraphics ();
      final RenderingHints rh = g.getRenderingHints ();
      rh.put (rh.KEY_ANTIALIASING, rh.VALUE_ANTIALIAS_ON);
      g.setRenderingHints (rh);
      return g;
    }
  }
}
