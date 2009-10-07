package cheloniidae;

import java.awt.Color;
import static java.lang.Math.*;

public abstract class IncidentAngleComputation {
  // Incident angle computation is about finding out how much light passes through a translucent material based on the angle. Here's why the angle matters.
  // Suppose you have a one-inch thick sheet of smoked glass:
  //
  //                Ray #1  |                                  Ray #2
  //                        |                                  /
  //                        |                                 /
  //                        | 90 degrees                     /  60 degrees
  //    +-------------------|-------------------------------/-----+
  //    |                   |                              /      |
  //    |                   |                             /       |
  //    |                   |                            /        |
  //    +-------------------|---------------------------/---------+
  //                        |                          /
  //
  // Looking through it vertically (ray #1) results in the attenuation from one inch of smoked glass. However, if we look through it from a different angle (ray
  // #2), we'll pass through more glass. In each case, the amount of glass traveled through is the secant of the ray's incident angle, so for ray 1 it would be
  // sec(90) = 1, and for ray 2 it would be sec(60) = 2 / sqrt(3). Conveniently, the secant of the angle is simply the reciprocal of the dot product of the
  // ray's direction and the surface normal of the glass.
  //
  // Slightly more difficult is figuring out how much light is left after traveling through x amount of material. Doing this requires a separable differential
  // equation:
  //
  //   dL
  //   -- = - t L
  //   dx
  //
  // where L(x) is the amount of light after traveling through x amount of glass, and t is the translucency of the glass. Solving yields:
  //
  //              - t x
  //   L(x) = C e
  //
  // Solving for C is simple if we make the assumption that ray #1 should perceive t translucency from one inch of glass. So we can solve with that constraint:
  //
  //           -t 1              t
  //   t = C e         =>   C = ---
  //                             -t
  //                            e

  public static final double perceivedTranslucency (final double thickness, final double opacity) {
    final double c = opacity / exp (-opacity);
    return c * exp (-thickness * opacity);
  }

  public static final Color adjustForTranslucency (final Color original, final double thickness) {
    final double originalOpacity = original.getAlpha () / 255.0;
    return new Color (original.getRed (), original.getGreen (), original.getBlue (),
                      (int) (255 * (1.0 - perceivedTranslucency (thickness, originalOpacity))));
  }

  public static final double planarThickness (final Vector surfaceNormal, final Vector v) {
    // This is a simplified way to compute the secant of the scalar angle between the two vectors.
    return surfaceNormal.length () * v.length () / Math.abs (surfaceNormal.dot (v));
  }

  public static final double cylindricalThickness (final Vector axis, final Vector v) {
    return 1.0 - axis.length () * v.length () / Math.abs (axis.dot (v));
  }
}
