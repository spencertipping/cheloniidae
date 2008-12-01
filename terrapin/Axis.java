// Terrapin Turtle System copyright 2006 by Spencer Tipping (all rights reserved)
// Written 08-02-2006; licensed under the LGPL, latest version

  package terrapin;

  import java.awt.*;
  import java.util.*;

/**
 * Add this class to a TurtleDrawingWindow to create an axis or axes.
 *
 * @author Spencer Tipping
 */

  public class Axis implements BackgroundObject {
    // Constants
      // Set any one of these bits to have the corresponding
      // axis on the screen.
      public static final int  X_AXIS   = 1 << 0;
      public static final int  Y_AXIS   = 1 << 1;
      public static final int  Z_AXIS   = 1 << 2;
    
    // Fields
      // This variable controls which axes are displayed on the screen.
      // It is a bitmask of the axial constants.
      protected int      axes           = Z_AXIS;
      
      // These variables control how large and dense the axis is.
      protected double    axisLength    = 400.0;

      // This variable controls how far apart the ticks are.
      protected double    tickSpacing   = 50.0;

      // This variable specifies what color the axis is.
      protected Color      axisColor    = new Color (0.3f, 0.4f, 0.6f, 0.4f);

      // This variable specifies the thickness of the lines.
      protected double    lineThickness = 0.5;

      // This variable specifies the length of the tick marks' crosshairs.
      protected double    tickSize      = 2.0;

      // This variable prevents us from having to reconstruct the list
      // during each screen refresh.
      protected LinkedList  cache       = null;

    // Constructors
      public Axis () {}

      public Axis (int _axes) {
        axes = _axes;
      }

    // Accessors
      public int        getAxes ()                                      {return axes;}
      public void       setAxes (int _axes)                             {axes = _axes;}

      public double     getAxisLength ()                                {return axisLength;}
      public void       setAxisLength (double _axisLength)              {axisLength = _axisLength;}

      public double     getTickSpacing ()                               {return tickSpacing;}
      public void       setTickSpacing (double _tickSpacing)            {tickSpacing = _tickSpacing;}

      public Color      getAxisColor ()                                 {return axisColor;}
      public void       setAxisColor (Color _axisColor)                 {axisColor = _axisColor;}
      
      public double     getLineThickness ()                             {return lineThickness;}
      public void       setLineThickness (double _lineThickness)        {lineThickness = _lineThickness;}

      public double     getTickSize ()                                  {return tickSize;}
      public void       setTickSize (double _tickSize)                  {tickSize = _tickSize;}

    // Methods
      // Grid management (available to user)
        public void reinitializeGrid () {
          // This method should be called whenever the grid's parameters
          // are changed at runtime.
          cache = null;
        }

        public LinkedList getLines () {
          // Draw large axis lines and then small cross lines for the
          // individual tick marks on the axes.

          if (cache != null)
            return cache;
          else {
            int total          = (int) (axisLength / tickSpacing);
            LinkedList result  = new LinkedList ();

            if ((axes & X_AXIS) != 0)
              result.add (new Line (
                -axisLength, 0, 500.0,
                 axisLength, 0, 500.0,
                lineThickness, axisColor));

            if ((axes & Y_AXIS) != 0)
              result.add (new Line (
                0, -axisLength, 500.0,
                0,  axisLength, 500.0,
                lineThickness, axisColor));

            if ((axes & Z_AXIS) != 0)
              result.add (new Line (
                0, 0, -axisLength + 500.0,
                0, 0,  axisLength + 500.0,
                lineThickness, axisColor));
            
            for (int i = -total; i <= total; i++) {
              if ((axes & X_AXIS) != 0)
                makeTick (i * tickSpacing, 0, 500.0, result, X_AXIS);

              if ((axes & Y_AXIS) != 0)
                makeTick (0, i * tickSpacing, 500.0, result, Y_AXIS);

              if ((axes & Z_AXIS) != 0)
                makeTick (0, 0, i * tickSpacing + 500.0, result, Z_AXIS);
            }

            return cache = result;
          }
        }

      // Axis management (hidden from user)
        protected void makeTick (
            double x, double y, double z, LinkedList target, int orientation) {
          // Draw two line segments.

          if (orientation != X_AXIS)
            target.add (new Line (x + tickSize, y, z, x - tickSize, y, z, lineThickness, axisColor));

          if (orientation != Y_AXIS)
            target.add (new Line (x, y + tickSize, z, x, y - tickSize, z, lineThickness, axisColor));

          if (orientation != Z_AXIS)
            target.add (new Line (x, y, z + tickSize, x, y, z - tickSize, lineThickness, axisColor));
        }

  }
