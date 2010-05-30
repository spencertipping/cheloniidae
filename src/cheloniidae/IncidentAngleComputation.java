package cheloniidae;

import java.awt.Color;
import static java.lang.Math.*;

public abstract class IncidentAngleComputation {
  public static final double lightTransmission (final double thickness, final double opacity) {
    final double c = (1.0 - opacity) / exp (-opacity);
    return c * exp (-thickness * opacity);
  }

  public static final Color adjustForThickness (final Color original, final double thickness) {
    final double originalOpacity  = original.getAlpha () / 255.0;
    final double perceivedOpacity = 1.0 - lightTransmission (thickness, originalOpacity);
    final double clippedOpacity   = perceivedOpacity > 1.0 ? 1.0 :
                                    perceivedOpacity < 0.0 ? 0.0 : perceivedOpacity;

    return new Color (original.getRed (), original.getGreen (), original.getBlue (), (int) (clippedOpacity * 255.0));
  }

  public static final double planarThickness (final Vector surfaceNormal, final Vector v) {
    // This is a simplified way to compute the secant of the scalar angle between the two vectors.
    return surfaceNormal.length () * v.length () / abs (surfaceNormal.dot (v));
  }

  public static final double cylindricalThickness (final Vector axis, final Vector v) {
    final double cosineTheta = axis.dot (v) / (axis.length () * v.length ());
    final double sineTheta   = sqrt (1.0 - cosineTheta * cosineTheta);
    return 1.0 / sineTheta;
  }
}
