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

  public CartesianLine (final Vector _v1, final Vector _v2, final double _width, final Color _color) {
    v1       = new Vector (_v1);
    v2       = new Vector (_v2);
    width    = _width;
    color    = _color;
    midpoint = v1.clone ().multiply (0.5).addScaled (v2, 0.5);
  }

  public double computeDepth (final Viewport v) {return v.transformPoint (midpoint).length ();}

  public void render (final Viewport v) {
    final Vector tv1 = v.transformPoint (v1);
    final Vector tv2 = v.transformPoint (v2);

    // If either point is behind the POV, then solve for z = 1.0. If both points are behind,
    // then the line does not get rendered.
    if (tv1.z > 0 || tv2.z > 0) {
      v.representativePoint (midpoint).representativePoint (v1).representativePoint (v2);

      if (tv1.z <= 0) {
        final double factor = (1.0 - tv1.z) / (tv2.z - tv1.z);
        tv1.multiply (1.0 - factor).addScaled (tv2, factor);
      } else if (tv2.z <= 0) {
        final double factor = (1.0 - tv2.z) / (tv1.z - tv2.z);
        tv2.multiply (1.0 - factor).addScaled (tv1, factor);
      }

      final double     thickness = 2.0 * v.scaleFactor () / (tv1.z + tv2.z);
      final Vector     pv1       = v.projectPoint (tv1);
      final Vector     pv2       = v.projectPoint (tv2);
      final Graphics2D g         = v.context ();

      final Vector transformedMidpoint = v.transformPoint (midpoint);
      final Vector transformedNormal   = tv1.clone ().subtract (tv2);
      final Color  newColor            = IncidentAngleComputation.adjustForThickness (color,
                                          IncidentAngleComputation.cylindricalThickness (
                                            transformedNormal, transformedMidpoint));

      g.setStroke (new BasicStroke ((float) Math.abs (thickness * width)));
      g.setColor  (newColor);
      g.drawLine  ((int) pv1.x, (int) pv1.y, (int) pv2.x, (int) pv2.y);
    }
  }
}
