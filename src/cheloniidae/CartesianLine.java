// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class CartesianLine extends ViewportCaching implements HasPerspectiveProjection, RenderAction {
  public final Vector v1;
  public final Vector v2;
  public final double width;
  public final Color  color;
  public final Vector midpoint;

  public CartesianLine (Vector _v1, Vector _v2, double _width, Color _color) {
    v1       = new Vector (_v1);
    v2       = new Vector (_v2);
    width    = _width;
    color    = _color;
    midpoint = v1.clone ().multiply (0.5).addScaled (v2, 0.5);
  }

  public double computeDepth (Viewport v) {return v.transformPoint (midpoint).length ();}

  public void render (Viewport v) {
    Vector pv1 = v.transformPoint (v1);
    Vector pv2 = v.transformPoint (v2);

    v.representativePoint (midpoint);

    // If either point is behind the POV, then solve for z = 1.0. If both points are behind,
    // then the line does not get rendered.
    if (pv1.z > 0 || pv2.z > 0) {
      if (pv1.z <= 0) {
        final double factor = (1.0 - pv1.z) / (pv2.z - pv1.z);
        pv1.multiply (1.0 - factor).addScaled (pv2, factor);
      } else if (pv2.z <= 0) {
        final double factor = (1.0 - pv2.z) / (pv1.z - pv2.z);
        pv2.multiply (1.0 - factor).addScaled (pv1, factor);
      }

      final double thickness = 2.0 * v.scaleFactor () / (pv1.z + pv2.z);

      pv1 = v.projectPoint (pv1);
      pv2 = v.projectPoint (pv2);

      Graphics2D g = v.context ();
      g.setStroke (new BasicStroke ((float) Math.abs (thickness * width)));
      g.setColor  (color);
      g.drawLine  ((int) pv1.x, (int) pv1.y, (int) pv2.x, (int) pv2.y);
    }
  }
}
