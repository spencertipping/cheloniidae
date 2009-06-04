// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae;

import java.awt.Color;

public final class Line {
  public Vector v1    = null;
  public Vector v2    = null;
  public double width = 2.0;
  public Color  color = null;

  // This is used in depth-sorting. (See LineProvider.java)
  double cachedDistance = 0.0;

  public Line (Vector _v1, Vector _v2, double _width, Color _color)
    {v1 = new Vector (_v1); v2 = new Vector (_v2); width = _width; color = _color;}

  public final Vector midpoint () {return new Vector (v1).multiply (0.5).addScaled (v2, 0.5);}
}
