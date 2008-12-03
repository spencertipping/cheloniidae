// Terrapin Turtle System copyright 2006 by Spencer Tipping
// Written 07-26-2006; licensed under the LGPL, latest version

  package terrapin;

  import java.awt.*;

/**
 * This class represents a single line that can be drawn by a turtle.
 *
 * @author Spencer Tipping
 */

  public class Line {
    // Fields
      double  x1        = 0.0;
      double  y1        = 0.0;
      double  z1        = 0.0;
      double  x2        = 0.0;
      double  y2        = 0.0;
      double  z2        = 0.0;
      
      // This is the color that the line should appear on the screen. It may
      // include an alpha component for translucent lines.
      Color  lineColor    = null;
        
      // This is the width of the line.
      double  width      = 2.0;

      // This is used in depth-sorting. (See TurtleCanvas.java)
      double cachedDistance = 0.0;

    // Constructors
      Line (double _x1, double _y1, double _z1,
            double _x2, double _y2, double _z2,
            double _width, Color _lineColor) {

        x1              = _x1;
        y1              = _y1;
        z1              = _z1;
        x2              = _x2;
        y2              = _y2;
        z2              = _z2;
        lineColor       = _lineColor;
        width           = _width;
      }

  }
