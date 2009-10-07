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
  //   -- = - o L
  //   dx
  //
  // where L(x) is the amount of light after traveling through x amount of glass, and o is the opacity of the glass. Solving yields:
  //
  //              - o x
  //   L(x) = C e
  //
  // Solving for C is simple if we make the assumption that ray #1 should perceive o opacity from one inch of glass. So we can solve with that constraint:
  //
  //               -o               1 - o
  //   1 - o = C e         =>   C = -----
  //                                  -o
  //                                 e

  public static final double lightTransmission (final double thickness, final double opacity) {
    final double c = (1.0 - opacity) / exp (-opacity);
    return c * exp (-thickness * opacity);
  }

  public static final Color adjustForThickness (final Color original, final double thickness) {
    final double originalOpacity = original.getAlpha () / 255.0;
    final double perceivedOpacity = 1.0 - lightTransmission (thickness, originalOpacity);
    final double clippedOpacity   = perceivedOpacity > 1.0 ? 1.0 :
                                    perceivedOpacity < 0.0 ? 0.0 : perceivedOpacity;

    return new Color (original.getRed (), original.getGreen (), original.getBlue (), (int) (clippedOpacity * 255.0));
  }

  public static final double planarThickness (final Vector surfaceNormal, final Vector v) {
    // This is a simplified way to compute the secant of the scalar angle between the two vectors.
    return surfaceNormal.length () * v.length () / Math.abs (surfaceNormal.dot (v));
  }

  public static final double cylindricalThickness (final Vector axis, final Vector v) {
    return 1.0 - axis.length () * v.length () / Math.abs (axis.dot (v));
  }
}
