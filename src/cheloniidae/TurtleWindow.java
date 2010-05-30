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
import java.util.Random;

public class TurtleWindow<T extends Turtle> extends Frame implements Viewport {
  public class RenderOperation extends Thread {
    protected final Viewport viewport;
    public RenderOperation (final Viewport v) {viewport = v;}

    public void run () {
      final Graphics2D c = context ();
      c.setColor (getBackground ());
      c.fillRect (0, 0, getWidth (), getHeight ());

      final SortedSet<RenderAction> actions        = turtles.actions (viewport);
      final Iterator<RenderAction>  actionIterator = actions.iterator ();

      int objectsDrawnSoFar = 0;
      while (! shouldCancel && actionIterator.hasNext ()) {
        actionIterator.next ().render (viewport);
        if (++objectsDrawnSoFar % drawingRefreshInterval == 0) repaint ();
      }

      if (! shouldCancel) {
        if (shouldShowObjectCount) setTitle ("Cheloniidae (" + objectsDrawnSoFar + " objects)");
        repaint ();
      }
    }
  }

  public class IntermediateRenderOperation extends Thread {
    public void run () {
      final Graphics2D c          = context ();
      final int        pointColor = (~ getBackground ().getRGB ()) & 0xffffff;
      c.setColor (getBackground ());
      c.fillRect (0, 0, getWidth (), getHeight ());

      for (int i = 0; ! shouldCancel && i < intermediatePointCloud.length; ++i)
        if (intermediatePointCloud[i] != null) {
          final Vector tp = transformPoint (intermediatePointCloud[i]);
          if (tp.z > 0.0) {
            final Vector pp = projectPoint (tp);
            if (pp.x >= 0.0 && pp.x < getWidth () && pp.y >= 0.0 && pp.y < getHeight ())
              offscreen.setRGB ((int) pp.x, (int) pp.y, pointColor);
          }
        }

      repaint ();
    }
  }

  protected int            drawingRefreshInterval    = 1000;
  protected boolean        shouldShowObjectCount     = true;

  protected BufferedImage  offscreen                 = null;
  protected Graphics2D     cachedContext             = null;
  protected TurtleGroup<T> turtles                   = new TurtleGroup<T> ();

  protected Vector         virtualPOV                = new Vector (0, 0, -500.0);
  protected Vector         virtualPOVUp              = new Vector (0, 1, 0);
  protected Vector         virtualPOVForward         = new Vector (0, 0, 1);

  protected Vector         minimumExtent             = new Vector (0, 0, 0);
  protected Vector         maximumExtent             = new Vector (0, 0, 0);

  protected Vector[]       intermediatePointCloud    = new Vector[5000];

  protected int            mouseDownX                = 0;
  protected int            mouseDownY                = 0;
  protected boolean        mouseDown                 = false;
  protected Thread         graphicsRequestRunner     = null;
  protected boolean        graphicsRequestCancelFlag = false;
  protected boolean        fisheye3D                 = false;
  protected long           lastChange                = 0;
  protected boolean        shouldCancel              = false;
  
  public TurtleWindow () {initialize ();}

  public int          drawingRefreshInterval     () {return drawingRefreshInterval;}
  public TurtleWindow drawingRefreshInterval     (final int _drawingRefreshInterval) 
    {drawingRefreshInterval = _drawingRefreshInterval; return this;}

  public boolean      shouldShowObjectCount      () {return shouldShowObjectCount;}
  public TurtleWindow shouldShowObjectCount      (final boolean _shouldShowObjectCount)
    {shouldShowObjectCount = _shouldShowObjectCount; return this;}

  public int          intermediatePointCloudSize () {return intermediatePointCloud.length;}
  public TurtleWindow intermediatePointCloudSize (final int _intermediatePointCloudSize)
    {intermediatePointCloud = new Vector[_intermediatePointCloudSize]; return this;}

  protected void handleResize () {
    offscreen     = new BufferedImage (super.getWidth (), super.getHeight (), BufferedImage.TYPE_3BYTE_BGR);
    cachedContext = null;
    enqueueGraphicsRefreshRequest (new RenderOperation (this));
  }

  protected void initialize () {
    final TurtleWindow t = this;

    super.addWindowListener (new WindowListener ()
      {public void windowClosing     (final WindowEvent e) {dispose ();}
       public void windowActivated   (final WindowEvent e) {}
       public void windowClosed      (final WindowEvent e) {}
       public void windowDeactivated (final WindowEvent e) {}
       public void windowDeiconified (final WindowEvent e) {}
       public void windowIconified   (final WindowEvent e) {}
       public void windowOpened      (final WindowEvent e) {}});

    super.addComponentListener (new ComponentListener ()
      {public void componentResized (final ComponentEvent e) {handleResize ();}
       public void componentMoved   (final ComponentEvent e) {}
       public void componentHidden  (final ComponentEvent e) {}
       public void componentShown   (final ComponentEvent e) {}});

    super.addMouseListener (new MouseListener () {public void mouseReleased (final MouseEvent e)
                                                    {mouseDown = false;
                                                     lastChange = System.currentTimeMillis ();
                                                     enqueueGraphicsRefreshRequest (new RenderOperation (t));}

                                                  public void mousePressed  (final MouseEvent e)
                                                    {mouseDown = true;
                                                     mouseDownX = e.getX ();
                                                     mouseDownY = e.getY ();}

                                                  public void mouseClicked  (final MouseEvent e) {}
                                                  public void mouseEntered  (final MouseEvent e) {}
                                                  public void mouseExited   (final MouseEvent e) {}});

    super.addMouseMotionListener (new MouseMotionListener () {
      public void mouseDragged (final MouseEvent e) {
        if (mouseDown) {
          final Vector virtualPOVRight = virtualPOVForward.cross (virtualPOVUp);
          final Vector center          = new Vector (maximumExtent).multiply (0.5).
                                                                    addScaled (minimumExtent, 0.5);
          final double factor          = e.isControlDown () ? 0.1 : 1.0;

          // A normal drag translates the view locally.
          if (! e.isShiftDown ())
            virtualPOV.addScaled (virtualPOVUp,    factor * (mouseDownY - e.getY ())).
                       addScaled (virtualPOVRight, factor * (e.getX () - mouseDownX));
          else {
            final double pitchAngle = (e.getY () - mouseDownY) * factor;
            final double turnAngle  = (e.getX () - mouseDownX) * factor;

            virtualPOV = virtualPOV.subtract (center).rotatedAbout (virtualPOVUp,    turnAngle).
                                                      rotatedAbout (virtualPOVRight, pitchAngle).add (center);

            virtualPOVForward = virtualPOVForward.rotatedAbout (virtualPOVUp,    turnAngle).
                                                  rotatedAbout (virtualPOVRight, pitchAngle).normalize ();
            virtualPOVUp      = virtualPOVUp.rotatedAbout      (virtualPOVRight, pitchAngle).normalize ();
          }

          lastChange = System.currentTimeMillis ();

          mouseDownX = e.getX ();
          mouseDownY = e.getY ();
          enqueueGraphicsRefreshRequest (new IntermediateRenderOperation ());
        }
      }
      public void mouseMoved (final MouseEvent e) {}
    });

    super.addMouseWheelListener (new MouseWheelListener  () {
      public void mouseWheelMoved (final MouseWheelEvent e) {
        virtualPOV.addScaled (virtualPOVForward, (e.isControlDown () ? -1 : -10) * e.getWheelRotation ());
        lastChange = System.currentTimeMillis ();
        enqueueGraphicsRefreshRequest (new IntermediateRenderOperation ());
      }});

    super.setSize       (600, 372);
    super.setTitle      ("Cheloniidae");
    super.setVisible    (true);
    super.setBackground (Color.WHITE);

    handleResize ();
  }

  public void update (final Graphics g) {paint (g);}
  public void paint  (final Graphics g) {g.drawImage (offscreen, 0, 0, null);}

  public TurtleWindow add (final T t) {turtles.turtles ().add (t); t.window (this); return this;}

  public void enqueueGraphicsRefreshRequest (final Thread t) {
    if (offscreen != null) {
      if (graphicsRequestRunner != null && graphicsRequestRunner.isAlive ()) {
        cancel ();
        try {graphicsRequestRunner.join ();}
        catch (final InterruptedException e) {}
      }

      shouldCancel = false;
      (graphicsRequestRunner = t).start ();
    }
  }

  public TurtleWindow pause (final long milliseconds) {
    enqueueGraphicsRefreshRequest (new RenderOperation (this));
    try {Thread.sleep (milliseconds);}
    catch (final InterruptedException e) {}
    return this;
  }

  public TurtleWindow cancel () {shouldCancel = true; return this;}

  public boolean shouldCancel () {return shouldCancel;}
  public long    lastChange   () {return lastChange;}
  public double  scaleFactor  () {return getHeight ();}

  public TurtleWindow representativePoint (final Vector v) {
    final int index = Math.abs (new Random ().nextInt ()) % intermediatePointCloud.length;
    if (intermediatePointCloud[index] == null || new Random ().nextDouble () > 0.97) intermediatePointCloud[index] = v;
    minimumExtent.componentwiseMinimum (v);
    maximumExtent.componentwiseMaximum (v);
    return this;
  }

  public Vector transformPoint (final Vector v)
    {return new Vector (v).subtract (virtualPOV).toCoordinateSpace (
      virtualPOVUp.cross (virtualPOVForward), virtualPOVUp, virtualPOVForward);}

  public Vector projectPoint (final Vector v)
    {return (fisheye3D ? new Vector (v).normalize () :
                         new Vector (v).divide (v.z)).multiply (
                           super.getHeight ()).add (new Vector (super.getWidth () >> 1, super.getHeight () >> 1, 0));}

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
