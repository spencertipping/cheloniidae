// Terrapin Turtle System copyright 2006 by Spencer Tipping
// Written 07-26-2006; licensed under the LGPL, latest version

  package terrapin;

  import java.awt.image.*;
  import java.awt.event.*;
  import java.awt.BasicStroke;
  import java.awt.Canvas;
  import java.awt.Color;
  import java.awt.Graphics;
  import java.awt.Graphics2D;
  import java.awt.RenderingHints;
  import java.util.*;

/**
 * This class implements a window into which turtles can draw. To use it, try
 * this code:
 *
 * <code>
 * Turtle t = new Turtle ();
 * TurtleCanvas c = new TurtleCanvas ();
 * c.add (t);
 * </code>
 *
 * @author Spencer Tipping
 */

  public class TurtleCanvas extends Canvas {
    // Fields
      // This image is used to store the output lines that the turtles make.
      // It is the first stage of the rendering process.
      protected BufferedImage turtleOutput                    = null;
        
      // This image is the turtle output plus the turtle images themselves.
      // I'm using it to provide double-buffered rendering.
      protected BufferedImage turtleLayer                     = null;
      
      // This list stores all of the lines that the turtles have drawn on the
      // screen. It's important for this to be an ArrayList because we sort it
      // every time the view angle is changed. A LinkedList would be much
      // slower for this purpose.
      protected ArrayList     lines                           = new ArrayList ();

      // This list stores all of the turtles that we should draw.
      protected List          turtles                         = new LinkedList ();

      // This list stores all of the background objects that we should
      // draw.
      protected List          backgrounds                     = new LinkedList ();

      // This is the number of lines that we have already drawn on the current
      // valid image. When we resize, we redraw the entire image, however we
      // don't want to do that every time the turtle makes a new line. So we
      // instead keep track of how many lines the turtle has already drawn,
      // and just draw the new ones.
      protected int           linesDrawn                      = 0;
      
      // This variable controls the depth at which the viewport is focused.
      protected int           viewportZoom                    = 0;
      
      // I'm implementing a scrollable viewport, so the user can drag the
      // image around with the mouse. These variables store the location of
      // the center point on the image. By default, that location is set to
      // center in the window (so each of these is width/2 and height/2,
      // respectively).
      protected int           viewportX                       = 0;
      protected int           viewportY                       = 0;

      // The viewportTheta and viewportPhi variables control the amount of
      // rotation in each axis. ViewportPhi is dominant over viewportTheta, as
      // is true in spherical coordinates.
      protected double        viewportTheta                   = 0.0;
      protected double        viewportPhi                     = 0.0;

      // These variables control where the viewer's eye is along the
      // viewport's rotated plane in 3D space.
      protected double        viewportPreProjectionX          = 0.0;
      protected double        viewportPreProjectionY          = 0.0;

      // Determines the default depth of rendered objects. This is useful for
      // determining the scale of the model being rendered, though it is
      // overridden when zooming.
      protected double        zBase                           = 500.0;

      // These variables are cached values for the foremost and backmost Z
      // values of the lines. They are used for theta and phi rotation.
      protected double        xMinimumExtent                  = 0.0;
      protected double        xMaximumExtent                  = 0.0;
      protected double        yMinimumExtent                  = 0.0;
      protected double        yMaximumExtent                  = 0.0;
      protected double        zMinimumExtent                  = zBase;
      protected double        zMaximumExtent                  = zBase;

      // These variables keep track of the location that the user pressed down
      // the mouse. We need to know this to determine how far to move the
      // screen (and in which direction) when they drag it (to support
      // viewport panning).
      protected int           mouseDownX                      = 0;
      protected int           mouseDownY                      = 0;
      
      // This variable is used to determine whether we should be considering
      // panning operations. It is true only when the mouse button is down.
      protected boolean       mouseDown                       = false;

      // We run the graphics refreshing in a separate thread so that we don't
      // bog down the AWT event queue.
      protected Thread        graphicsRequestRunner           = null;

      // If true, then we jump out of any graphics methods and cancel.
      protected boolean       graphicsRequestCancel           = false;

      // By default, Terrapin uses one vanishing point for rendering. This is
      // advantageous for two-dimensional models, whose proportions will be
      // preserved as if they were rendered orthographically onto the
      // two-dimensional plane. However, it is not technically correct. Two
      // parallel lines will appear to converge even if their depths are not
      // constant simply due to their distances from the viewer. Setting the
      // fisheye3D option enables this consideration to be observed.
      protected boolean       fisheye3D                       = false;

      // These values control how turtles are displayed to the user. Each
      // turtle is rendered as a circle with a line sticking out, and the line
      // indicates the turtle's heading. (This is computed by the turtle's
      // would-be position if it were to move a certain distance.)
      protected double        turtleDisplayRadius             = 4.0;
      protected double        turtleDisplayHeadingLength      = 16.0;
      protected int           turtleDisplayAlpha              = 128;

      // These are parameters of the line-drawing algorithm. The refresh
      // interval specifies how frequently the screen is updated while lines
      // are still being drawn. The intermediate lines parameter determines
      // the maximum number of lines to be drawn while the user is dragging
      // the mouse, and the line-point distance is the distance between dots
      // of a manually-drawn line.
      protected int           drawingRefreshIntervalInLines   = 10000;
      protected int           drawingIntermediateLines        = 10000;
      protected int           drawingLinePointDistance        = 16;

    // Constructors
      public TurtleCanvas () {
        // Do all event initialization and such.
        initialize ();
      }

    // Accessors
      public boolean  getFisheye3D ()                                                         {return fisheye3D;}
      public void     setFisheye3D (boolean _fisheye3D)                                       {fisheye3D = _fisheye3D;}

      public double   getZBase ()                                                             {return zBase;}
      public void     setZBase (double _zBase)                                                {zBase = _zBase;}

      public double   getTurtleDisplayRadius ()                                               {return turtleDisplayRadius;}
      public void     setTurtleDisplayRadius (double _turtleDisplayRadius)                    {turtleDisplayRadius = _turtleDisplayRadius;}
      public double   getTurtleDisplayHeadingLength ()                                        {return turtleDisplayHeadingLength;}
      public void     setTurtleDisplayHeadingLength (double _turtleDisplayHeadingLength)      {turtleDisplayHeadingLength = _turtleDisplayHeadingLength;}
      public int      getTurtleDisplayAlpha ()                                                {return turtleDisplayAlpha;}
      public void     setTurtleDisplayAlpha (int _turtleDisplayAlpha)                         {turtleDisplayAlpha = _turtleDisplayAlpha;}

      public int      getDrawingRefreshIntervalInLines ()                                     {return drawingRefreshIntervalInLines;}
      public void     setDrawingRefreshIntervalInLines (int _drawingRefreshIntervalInLines)   {drawingRefreshIntervalInLines = _drawingRefreshIntervalInLines;}
      public int      getDrawingIntermediateLines ()                                          {return drawingIntermediateLines;}
      public void     setDrawingIntermediateLines (int _drawingIntermediateLines)             {drawingIntermediateLines = _drawingIntermediateLines;}
      public int      getDrawingLinePointDistance ()                                          {return drawingLinePointDistance;}
      public void     setDrawingLinePointDistance (int _drawingLinePointDistance)             {drawingLinePointDistance = _drawingLinePointDistance;}

    // Methods
      // Canvas management (hidden from user)
        protected void regenerateImages () {
          // Reallocate both the turtle output layer and the turtle layer.

          if (super.getWidth () > 0 && super.getHeight () > 0) {
            turtleOutput = new BufferedImage (super.getWidth (), super.getHeight (), BufferedImage.TYPE_3BYTE_BGR);
            turtleLayer  = new BufferedImage (super.getWidth (), super.getHeight (), BufferedImage.TYPE_3BYTE_BGR);
          }
        }

        protected void handleResize () {
          // Set up the viewport dimensions. I know that we snap back to
          // center on a resize, and while this may not be desirable, I
          // would consider it to be so because if the user for some reason
          // pans too far off center and loses the image, this is a convenient
          // way to restore it.
          viewportX = super.getWidth () / 2;
          viewportY = super.getHeight () / 2;

          // Regenerate the images and repaint the screen.
          regenerateImages ();
          enqueueGraphicsRefreshRequest (true, true);
        }
        
        protected void initialize () {
          // Step 1. Add listeners to the canvas.

          // Provide a backreference for all of the anonymous classes.
          // Java's rules say that it must be final.
          final TurtleCanvas _this = this;
          
          super.addComponentListener (new ComponentListener () {
            public void componentResized  (ComponentEvent e) {
              _this.handleResize ();
            }

            public void componentMoved    (ComponentEvent e) {}
            public void componentHidden   (ComponentEvent e) {}
            public void componentShown    (ComponentEvent e) {}
          });

          super.addMouseListener (new MouseListener () {
            public void mouseReleased (MouseEvent e) {
              mouseDown = false;
              _this.enqueueGraphicsRefreshRequest (true, true);
            }

            public void mousePressed (MouseEvent e) {
              mouseDown = true;
              mouseDownX = e.getX ();
              mouseDownY = e.getY ();
            }

            public void mouseClicked (MouseEvent e) {}
            public void mouseEntered (MouseEvent e) {}
            public void mouseExited  (MouseEvent e) {}
          });

          super.addMouseMotionListener (new MouseMotionListener () {
            public void mouseDragged (MouseEvent e) {
              if (mouseDown) {
                if (e.isShiftDown () && e.isControlDown ()) {
                  // Zoom in and out.

                  viewportZoom += e.getY () - mouseDownY;
                } else if (e.isShiftDown ()) {
                  // Shift the rotation coefficients.

                  viewportTheta += e.getX () - mouseDownX;
                  viewportPhi += e.getY () - mouseDownY;
                } else if (e.isControlDown ()) {
                  // Shift the 3D viewport plane.

                  viewportPreProjectionX += e.getX () - mouseDownX;
                  viewportPreProjectionY += e.getY () - mouseDownY;
                } else {
                  // Shift the viewport over.

                  viewportX += e.getX () - mouseDownX;
                  viewportY += e.getY () - mouseDownY;
                }

                // Now, move the effective click location. This way,
                // we can move the viewport again by clicking and dragging
                // and it won't start back at the center of the window.
                mouseDownX = e.getX ();
                mouseDownY = e.getY ();

                // Show the user a snapshot of the image.
                _this.enqueueGraphicsRefreshRequest (true, false);
              }
            }

            public void mouseMoved (MouseEvent e) {}
          });

          // Step 2. Generate the initial images and paint the screen.
          super.setBackground (Color.WHITE);

          regenerateImages ();
          enqueueGraphicsRefreshRequest (true, true);
        }

        protected void initializeAntialiasing (Graphics2D g) {
          // Due to the architecture of rendering hints, we must first
          // load them into a variable, then change them, and finally
          // put them back into the graphics context in order to commit
          // changes. Otherwise, this would be a one-liner.

          RenderingHints rh = g.getRenderingHints ();
          rh.put (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
          g.setRenderingHints (rh);
        }

      // Graphical methods (available to user)
        public void clear () {
          // Clear off the background image and the list of lines.
          lines.clear ();
          zMinimumExtent = zMaximumExtent = zBase;

          // Once the list is clear, ask the window to refresh the graphics.
          // Since we've told it to redraw all lines, the screen will be clear.
          enqueueGraphicsRefreshRequest (true, false);
        }

        public void update (Graphics g) {
          paint (g);
        }

        public void paint (Graphics g) {
          g.drawImage (turtleLayer, 0, 0, null);
        }

        public void enqueueGraphicsRefreshRequest (boolean drawAllLines, boolean antialiased) {
          // If the images are not yet initialized, then don't do anything.

          if (turtleOutput != null && turtleLayer != null) {
            // If we currently have a graphics request going, then
            // kill that thread and start over.
            if (graphicsRequestRunner != null && graphicsRequestRunner.isAlive ()) {
              graphicsRequestCancel = true;

              // Wait for the graphics request runner to die.
              try {
                graphicsRequestRunner.join ();
              } catch (InterruptedException e) {}
            }

            graphicsRequestCancel = false;

            final boolean      _drawAllLines = drawAllLines;
            final boolean      _antialiased  = antialiased;
            final TurtleCanvas _this         = this;
            graphicsRequestRunner = new Thread (new Runnable () {
              public void run () {
                synchronized (lines) {
                  _this.drawLines   (_drawAllLines, _antialiased);
                  _this.drawTurtles (_antialiased);
                  _this.repaint     ();
                }
              }
            });

            graphicsRequestRunner.start ();
          }
        }
    
      // 3D methods (hidden from user)
        protected double lineDistance (Line l) {
          Point3D transformed = new Point3D ((l.x1 + l.x2) / 2.0,
                                             (l.y1 + l.y2) / 2.0,
                                             (l.z1 + l.z2) / 2.0);

          transformPoint (transformed);
          return transformed.length ();
        }

        protected final void swap (ArrayList l, int a, int b) {
          Object ltmp = l.get (a);
          l.set (a, l.get (b));
          l.set (b, ltmp);
        }

        protected void quickSort (ArrayList l, int a, int b) {
          // This quicksort algorithm keys on the cached distance
          // of each line in l. It sorts in reverse, so greater
          // distances are sorted before lesser distances.

          if (a < b && !graphicsRequestCancel) {
            int ia = a + 1;
            int ib = b + 1;
            int pivot = (a + b) / 2;

            swap (l, a, pivot);

            while (ia < ib && !graphicsRequestCancel) {
              // Bug fix: Sometimes the view takes a while to
              // respond. To fix this, we can break out of the
              // while loops as soon as the graphics cancel
              // flag is set.
              while (ia < b &&
                     ((Line) lines.get (ia)).cachedDistance > ((Line) lines.get (a)).cachedDistance &&
                     !graphicsRequestCancel)
                ia++;

              while (((Line) lines.get (--ib)).cachedDistance < ((Line) lines.get (a)).cachedDistance &&
                     !graphicsRequestCancel);

              if (ia < ib)
                swap (l, ia, ib);
            }

            swap (l, a, ib);

            quickSort (l, a, ib - 1);
            quickSort (l, ib + 1, b);
          }
        }

        protected void resortLines () {
          // Sorts the lines in order of distance from the viewport.
          // We do this so that when they are rendered, the lines don't overlap
          // each other unnaturally.
          //
          // This isn't a foolproof algorithm; there are cases where it will
          // make mistakes. However, it uses a decent heuristic that the center
          // of the line is what determines its distance.
          //
          // This algorithm does not allocate memory, which should
          // noticeably save time on large drawings.

          synchronized (lines) {
            for (int i = 0; i < lines.size () && !graphicsRequestCancel; i++)
              ((Line) lines.get (i)).cachedDistance = lineDistance ((Line) lines.get (i));

            quickSort (lines, 0, lines.size () - 1);
          }
        }

        protected synchronized void transformPoint (Point3D p) {
          // This method considers the values of viewportTheta, viewportPhi,
          // and viewportX and viewportY and transforms a point
          // in space (p) to a point in the displayable 3D viewspace.

          p.X -= (xMinimumExtent + xMaximumExtent) / 2.0;
          p.Y -= (yMinimumExtent + yMaximumExtent) / 2.0;
          p.Z -= (zMinimumExtent + zMaximumExtent) / 2.0;

          double ny = p.Y * Math.cos (Math.toRadians (viewportPhi)) -
                      p.Z * Math.sin (Math.toRadians (viewportPhi));
          double nz = p.Y * Math.sin (Math.toRadians (viewportPhi)) +
                      p.Z * Math.cos (Math.toRadians (viewportPhi));

          p.Y = ny;
          p.Z = nz;

          double nx = p.X * Math.cos (Math.toRadians (viewportTheta)) -
                      p.Z * Math.sin (Math.toRadians (viewportTheta));
          nz = p.X * Math.sin (Math.toRadians (viewportTheta)) +
               p.Z * Math.cos (Math.toRadians (viewportTheta));
        
          p.X = nx;
          p.Z = nz;
      
          p.X += (xMinimumExtent + xMaximumExtent) / 2.0 + viewportPreProjectionX;
          p.Y += (yMinimumExtent + yMaximumExtent) / 2.0 + viewportPreProjectionY;
          p.Z += (zMinimumExtent + zMaximumExtent) / 2.0 + viewportZoom;
        }

        protected void renderSloppyLine (BufferedImage target, Color c, int x1, int y1, int x2, int y2) {
          // This method renders a single line sloppily onto an image.
          // Basically, it keeps filling in points until no two points
          // are more than a few pixels apart. It uses binary splitting.

          if ((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2) > drawingLinePointDistance) {
            renderSloppyLine (target, c, x1, y1, (x1 + x2) / 2, (y1 + y2) / 2);
            renderSloppyLine (target, c, (x1 + x2) / 2, (y1 + y2) / 2, x2, y2);
          } else
            target.setRGB ((x1 + x2) / 2, (y1 + y2)  / 2, c.getRGB ());
        }
        
        protected double projectPoint (Point3D p) {
          // Performs an in-place projection of the point p into
          // 2D space. Returns the effective distance of the point
          // from the viewer, given the assumptions we're making
          // about the 3D perspective projection.
          
          if (fisheye3D) {
            double l = p.length ();

            p.X *= zBase / l;
            p.Y *= zBase / l;

            return l;
          } else {
            p.X *= zBase / p.Z;
            p.Y *= zBase / p.Z;

            return p.Z;
          }
        }

        protected void renderLine (Line l, Graphics2D g, BufferedImage target, boolean antialiasing) {
          // This method renders a single line onto the screen.
          // I'm assuming that the graphics context has already been
          // initialized as far as antialiasing is concerned.

          Point3D newPoint1 = new Point3D (l.x1, l.y1, l.z1);
          Point3D newPoint2 = new Point3D (l.x2, l.y2, l.z2);

          transformPoint (newPoint1);
          transformPoint (newPoint2);

          // If either point is behind the camera (but not both), then solve
          // for the point at Z = 1. We then use that point for the end of the
          // line.

          if (newPoint1.Z < 0.0 && newPoint2.Z < 0.0)
            return;
          else if (newPoint1.Z < 0.0 || newPoint2.Z < 0.0) {
            double difference_in_z = newPoint1.Z - newPoint2.Z;
            
            if (newPoint1.Z < 0.0) {
              double factor = Math.abs ((1.0 - newPoint1.Z) / difference_in_z);
              newPoint1.multiply (1.0 - factor);
              newPoint1.addScaled (newPoint2, factor);
            } else {
              double factor = Math.abs ((1.0 - newPoint2.Z) / difference_in_z);
              newPoint2.multiply (1.0 - factor);
              newPoint2.addScaled (newPoint1, factor);
            }
          }

          // Perform perspective transformation.
          double dp1 = projectPoint (newPoint1);
          double dp2 = projectPoint (newPoint2);

          if (antialiasing) {
            // Do nice rendering with strokes and such.
            g.setStroke (new BasicStroke ((float) Math.abs (l.width * zBase * 2.0 / 
                                                            (dp1 + dp2))));
            g.setColor (l.lineColor);

            g.drawLine ((int) newPoint1.X + viewportX, (int) newPoint1.Y + viewportY,
                        (int) newPoint2.X + viewportX, (int) newPoint2.Y + viewportY);
          } else
            // Do a sloppy line.
            //
            // The check for in-boundedness is here for optimization reasons. If
            // we put it at the lowest level pixel-render, then every pixel would
            // have potentially four comparisons added to it. But here, we can get
            // away with eight for an entire line.

            if (newPoint1.X + viewportX > 0 && newPoint1.X + viewportX < target.getWidth () &&
                newPoint1.Y + viewportY > 0 && newPoint1.Y + viewportY < target.getHeight () &&
                newPoint2.X + viewportX > 0 && newPoint2.X + viewportX < target.getWidth () &&
                newPoint2.Y + viewportY > 0 && newPoint2.Y + viewportY < target.getHeight ())

              renderSloppyLine (target, l.lineColor, 
                (int) newPoint1.X + viewportX, (int) newPoint1.Y + viewportY,
                (int) newPoint2.X + viewportX, (int) newPoint2.Y + viewportY);
        }

        void updateExtents (double x1, double x2,
                            double y1, double y2,
                            double z1, double z2) {
          // We use extents to figure out the center of rotation. The camera should
          // rotate around the center of the bounding box of the figure.

          // X
          if (x1 < xMinimumExtent) xMinimumExtent = x1;
          if (x1 > xMaximumExtent) xMaximumExtent = x1;
          if (x2 < xMinimumExtent) xMinimumExtent = x2;
          if (x2 > xMaximumExtent) xMaximumExtent = x2;

          // Y
          if (y1 < yMinimumExtent) yMinimumExtent = y1;
          if (y1 > yMaximumExtent) yMaximumExtent = y1;
          if (y2 < yMinimumExtent) yMinimumExtent = y2;
          if (y2 > yMaximumExtent) yMaximumExtent = y2;
        
          // Z
          if (z1 < zMinimumExtent) zMinimumExtent = z1;
          if (z1 > zMaximumExtent) zMaximumExtent = z1;
          if (z2 < zMinimumExtent) zMinimumExtent = z2;
          if (z2 > zMaximumExtent) zMaximumExtent = z2;
        }

      // Graphical methods (hidden from user)
        protected void drawTurtles (boolean antialiased) {
          // Step 1: Set up antialiasing.
          Graphics2D g = (Graphics2D) turtleLayer.getGraphics ();
          if (antialiased) initializeAntialiasing (g);

          // Step 2: Draw the turtle output on the image.
          g.drawImage (turtleOutput, 0, 0, null);

          // Step 3: Draw each turtle.
          ListIterator i = turtles.listIterator ();
          while (i.hasNext () && !graphicsRequestCancel) {
            TurtleBase t = (TurtleBase) i.next ();

            // Draw only the turtles that are visible.
            if (t.getVisible ()) {
              // I want to draw turtles in their body colors. So I'm taking
              // the RGB components from the turtle and putting in an alpha
              // value chosen by the user.
              g.setColor (new Color (
                    t.getBodyColor ().getRed (),
                    t.getBodyColor ().getGreen (),
                    t.getBodyColor ().getBlue (),
                    turtleDisplayAlpha));

              // Turtles are right now represented by 8x8-unit circles.
              Point3D turtleLocation = new Point3D (t.getX (), t.getY (), t.getZ ());
              transformPoint (turtleLocation);
              double turtleSize = zBase / projectPoint (turtleLocation);

              g.setStroke (new BasicStroke ((float) Math.abs (turtleSize)));
              g.drawOval ((int) (turtleLocation.X - turtleDisplayRadius * turtleSize) + viewportX,
                          (int) (turtleLocation.Y - turtleDisplayRadius * turtleSize) + viewportY,
                          (int) (turtleDisplayRadius * 2.0 * turtleSize), (int) (turtleDisplayRadius * 2.0 * turtleSize));
              
              // Show the turtle's heading with a 15-unit line.
              Point3D turtleVector = t.computeTurtleVector (turtleDisplayHeadingLength);

              renderLine (new Line (
                    t.getX (), t.getY (), t.getZ (),
                    t.getX () + turtleVector.X, t.getY () + turtleVector.Y, t.getZ () + turtleVector.Z,
                    1.0, g.getColor ()), g, turtleLayer, antialiased);
            }
          }
        }

        protected void drawBackgroundObjects (boolean antialiased) {
          // Step 1. Set up antialiasing.

          Graphics2D g = (Graphics2D) turtleOutput.getGraphics ();
          if (antialiased) initializeAntialiasing (g);

          // Step 2. Clear off the image.
          g.setColor (super.getBackground ());
          g.fillRect (0, 0, turtleOutput.getWidth (), turtleOutput.getHeight ());

          // Step 3. Iterate through the background objects and
          // draw each one of them.
          Iterator i = backgrounds.listIterator ();
          while (i.hasNext ()) {
            Iterator j = ((BackgroundObject) i.next ()).getLines ().listIterator ();
            while (j.hasNext ())
              renderLine ((Line) j.next (), g, turtleOutput, antialiased);
          }
        }
        
        protected void drawLines (boolean startOver, boolean antialiased) {
          // Step 1: Set up antialiasing.

          Graphics2D g = (Graphics2D) turtleOutput.getGraphics ();
          int skip = 1;

          if (antialiased)
            initializeAntialiasing (g);
          else
            // Cap the number of lines drawn during an intermediate render.
            if (lines.size () > drawingIntermediateLines)
              skip = lines.size () / drawingIntermediateLines;

          // Step 2: If we are starting over, then redraw the background image
          // and set the linesDrawn counter to 0.
          if (startOver) {
            // Clear off the background image.

            drawBackgroundObjects (antialiased);
            linesDrawn = 0;
            if (antialiased) resortLines ();
          }

          //
          // Step 3: Draw the lines onto the image.
          //

          synchronized (lines) {
            if (lines.size () > linesDrawn) {
              if (linesDrawn > 0)
                while (linesDrawn < lines.size () && !graphicsRequestCancel)
                  renderLine ((Line) lines.get (linesDrawn++), g, turtleOutput, antialiased);
              else {
                for (int i = 0; i < lines.size () && !graphicsRequestCancel; i += skip) {
                  renderLine ((Line) lines.get (i), g, turtleOutput, antialiased);

                  if (antialiased && i % drawingRefreshIntervalInLines == 0) {
                    turtleLayer.getGraphics ().drawImage (turtleOutput, 0, 0, null);
                    repaint ();
                  }
                }

                if (!graphicsRequestCancel) linesDrawn = lines.size ();
              }
            }
          }
        }

      // Turtle management (available to user)
        public void add (TurtleBase t) {
          t.setCanvas (this);
          turtles.add (t);
        }

        
      // Background management (available to user)
        public void add (BackgroundObject b) {
          backgrounds.add (b);
        }

      // Turtle interfacing (hidden from user)
        void add (Line l) {
          synchronized (lines) {
            lines.add (l);
          }

          updateExtents (l.x1, l.x2, l.y1, l.y2, l.z1, l.z2);
          enqueueGraphicsRefreshRequest (false, true);
        }

        boolean shouldDelayMoves () {
          return super.isVisible () && super.getParent () != null && super.getParent ().isVisible ();
        }

        void turtleMoveCompleted (TurtleBase t) {
          enqueueGraphicsRefreshRequest (false, true);
        }

  }
