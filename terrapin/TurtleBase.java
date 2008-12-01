// Terrapin Turtle System copyright 2006 by Spencer Tipping (all rights reserved)
// Written 07-26-2006; licensed under LGPL, latest version

  package terrapin;
  
  import java.awt.Color;
  import java.util.*;

/**
 * Provides a position, color, pen up/down status, and canvas interfacing for a turtle.
 * Most turtle classes will want to subclass this.
 *
 * @author spencer
 */

  public abstract class TurtleBase {
    // Fields
      // These variables store the location of the turtle in 3D space.
      protected double                  x               = 0.0;
      protected double                  y               = 0.0;
      protected double                  z               = 500.0;

      // This variable stores the heading of the turtle in degrees. It is never
      // corrected modulo 360, so it may contain values such as -180.0 or 540.0.
      protected double                  headingTheta    = 0.0;
        
      // This variable stores the heading of the turtle on the r-z plane.
      protected double                  headingPhi      = 0.0;

      // This is used for orthogonal-planar coordinates. Xi is the elevatory
      // skew of the cylinder.
      protected double                  headingXi       = 0.0;
      
      // This is the color of the turtle as displayed on the screen.
      protected Color                   bodyColor       = Color.BLUE;

      // This is the color that the turtle will draw. By default, it is set to
      // black, contrasting with the TurtleDrawingWindow's default background
      // of white.
      protected Color                   penColor        = Color.BLACK;

      // If the pen is down, then the turtle will draw when it moves. Otherwise,
      // the turtle will not leave any marks.
      protected boolean                 penDown         = true;

      // This specifies line width when the turtle is drawing.
      protected double                  penSize         = 2.0;

      // This is the window into which to draw. It receives a notification
      // when we make a new line by a call to drawLines (false). (The false
      // means that we don't redraw all lines, but just new ones)
      protected TurtleCanvas            canvas          = null;

      // This is the amount of time slept per move or turn command.
      protected int                     delayPerMove    = 250;

      // If true, the turtle is shown in the TurtleDrawingWindow. Otherwise,
      // the turtle is not shown.
      protected boolean                 visible         = true;

      // This is used to store turtle location/angle information.
      protected Stack                   stkTurtleStates = new Stack ();

    // Constructors
      public TurtleBase () {}

      public TurtleBase (double _x, double _y, double _z) {
        x = _x;
        y = _y;
        z = _z;
      }

    // Accessors
      public double     getX ()                                 {return x;}
      public void       setX (double _x)                        {x = _x;}
      public double     getY ()                                 {return y;}
      public void       setY (double _y)                        {y = _y;}
      public double     getZ ()                                 {return z;}
      public void       setZ (double _z)                        {z = _z;}
      
      public Color      getBodyColor ()                         {return bodyColor;}
      public void       setBodyColor (Color _bodyColor)         {bodyColor = _bodyColor;}

      public Color      getPenColor ()                          {return penColor;}
      public void       setPenColor (Color _penColor)           {penColor = _penColor;}
      
      public boolean    getPenDown ()                           {return penDown;}
      public void       setPenDown (boolean _penDown)           {penDown = _penDown;}
      public void       penDown ()                              {setPenDown (true);}
      public void       penUp ()                                {setPenDown (false);}
     
      public double     getPenSize ()                           {return penSize;}
      public void       setPenSize (double _penSize)            {penSize = _penSize;}
    
      public int        getDelayPerMove ()                      {return delayPerMove;}
      public void       setDelayPerMove (int _delayPerMove)     {delayPerMove = _delayPerMove;}

      public boolean    getVisible ()                           {return visible;}
      public void       setVisible (boolean _visible)           {visible = _visible;}

      void              setCanvas (TurtleCanvas _canvas)        {canvas = _canvas;}
      
    // Methods
      // Turtle management (hidden from user)
        protected void drawLine (double x1, double y1, double z1,
                                 double x2, double y2, double z2) {
          // Step 1: Make sure that the pen is down. If it isn't, then 
          //         all we need to do is to put in a delay.
          if (penDown) {
            // Step 2: Make sure we have a window and a line list. If
            //         we don't, then print a warning.
            if (canvas != null)
              // Add the new line to the list and prompt the turtle drawing
              // window to refresh its display.
              canvas.add (new Line (x1, y1, z1, x2, y2, z2, penSize, penColor));
            else
              System.err.println ("Warning: Turtle does not belong to a turtle canvas.");
          }
        }

        protected void startMove () {
          // Create the delay per move here. Delays occur only when the canvas
          // signals that they should occur. Generally, this is when the canvas
          // is visible.

          if (delayPerMove > 0 && canvas.shouldDelayMoves ())
            try {
              Thread.sleep (delayPerMove);
            } catch (InterruptedException e) {}
        }
        
        protected void finishMove () {
          // This method should be called whenever a move or turn operation is
          // completed. We tell the window to refresh its graphics.
        
          canvas.turtleMoveCompleted (this);
        }

      // Turtle information (available to user)
        public abstract Point3D computeTurtleVector (double distance);
        
      // Turtle movement commands (available to user)
      //
      // Each one of these commands returns the turtle again, so that commands may
      // be conveniently chained like this:
      //
      //   t.move (100).jump (60).move (-100);

        public TurtleBase move (double distance) {
          return move (distance, false);
        }
        
        public TurtleBase jump (double distance) {
          return move (distance, true);
        }
        
        public TurtleBase move (double distance, boolean invisible) {
          startMove ();

          Point3D delta = computeTurtleVector (distance);

          if (visible) drawLine (x, y, z, x + delta.X, y + delta.Y, z + delta.Z);

          x += delta.X;
          y += delta.Y;
          z += delta.Z;
          
          finishMove ();

          return this;
        }

        public TurtleBase moveTo (double nx, double ny, double nz) {
          startMove ();
          drawLine (x, y, z, nx, ny, nz);
          x = nx;
          y = ny;
          z = nz;
          finishMove ();
          return this;
        }

        public TurtleBase jumpTo (double nx, double ny, double nz) {
          startMove ();
          x = nx;
          y = ny;
          z = nz;
          finishMove ();
          return this;
        }

  }
