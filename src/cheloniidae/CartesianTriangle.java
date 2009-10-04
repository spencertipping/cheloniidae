// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class CartesianTriangle extends ViewportCaching implements HasPerspectiveProjection, RenderAction {
  public final Vector v1;
  public final Vector v2;
  public final Vector v3;
  public final Color  color;
  public final Vector midpoint;

  public CartesianTriangle (final Vector _v1, final Vector _v2, final Vector _v3, final Color _color) {
    v1       = new Vector (_v1);
    v2       = new Vector (_v2);
    v3       = new Vector (_v3);
    color    = _color;
    midpoint = v1.clone ().multiply (1.0 / 3.0).addScaled (v2, 1.0 / 3.0).addScaled (v3, 1.0 / 3.0);
  }

  public double computeDepth (final Viewport v) {return v.transformPoint (midpoint).length ();}

  public void render (final Viewport v) {
    final Vector tv1 = v.transformPoint (v1);
    final Vector tv2 = v.transformPoint (v2);
    final Vector tv3 = v.transformPoint (v3);

    v.representativePoint (midpoint).representativePoint (v1).representativePoint (v2).representativePoint (v3);

    // Render the triangle only when all three points are in front of the camera. I'm being lazy here -- there is a way to solve for the points' projections to
    // z = 1 like we did for cartesian lines, but the number of cases is much larger.
    if (tv1.z > 0 && tv2.z > 0 && tv3.z > 0) {
      final Vector     pv1 = v.projectPoint (tv1);
      final Vector     pv2 = v.projectPoint (tv2);
      final Vector     pv3 = v.projectPoint (tv3);
      final Graphics2D g   = v.context ();

      g.setColor (color);
      g.fill     (new Polygon (new int[] {(int) pv1.x, (int) pv2.x, (int) pv3.x},
                               new int[] {(int) pv1.y, (int) pv2.y, (int) pv3.y}, 3));
    }
  }
}
