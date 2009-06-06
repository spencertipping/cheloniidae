import cheloniidae.*;
import java.awt.Color;

public class circles {
  public static void main (String[] args) {
    TurtleDrawingWindow w = new TurtleDrawingWindow ();
    RotationalCartesianTurtle t = new RotationalCartesianTurtle ();

    t.lineSize (0.05);

    w.add (t).setVisible (true);
    section (t, w, 50, 3);
  }

  public static void section (RotationalCartesianTurtle t, TurtleDrawingWindow w, double scale, int recursionLevel) {
    for (int i = 0; i < 15; ++i) {
      t.move (scale).turn ((recursionLevel & 1) == 0 ? 36 : -36).pitch (1);
      if (recursionLevel > 0) section (t, w, scale / 3.0, recursionLevel - 1);
    }
  }
}
