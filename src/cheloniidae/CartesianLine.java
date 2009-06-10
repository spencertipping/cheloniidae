// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae;

import java.awt.Color;

public class CartesianLine implements HasPerspectiveProjection {
  public final Vector v1;
  public final Vector v2;
  public final double width;
  public final Color  color;
  public final Vector midpoint;

  private double   cachedDistance;
  private Viewport cachedViewport;

  public CartesianLine (Vector _v1, Vector _v2, double _width, Color _color) {
    v1 = new Vector (_v1);
    v2 = new Vector (_v2);
    width = _width;
    color = _color;
    midpoint = new Vector (v1).add (v2).multiply (0.5);
  }

  public double depth (Viewport v) {
    double result = (cachedViewport == v || cachedViewport.equals (v)) ? cachedDistance : (cachedDistance = v.transformPoint (midpoint).length ());
    cachedViewport = v;
    return result;
  }
}
