import cheloniidae.*;
import java.awt.*;

public class donut {
  public static void main (String[] args) {
    TurtleDrawingWindow       w = new TurtleDrawingWindow ();
    RotationalCartesianTurtle t = new RotationalCartesianTurtle ();

    w.add (t);
    w.setVisible (true);

    t.lineSize (0.5).lineColor (new Color (0.5f, 0.65f, 0.55f, 0.5f));

    for (int i = 0; i < 36; i++) {
      t.jump (100);
      for (int j = 0; j < 90; j++) t.move (2.0).pitch (4.0);
      t.jump (-100).turn (10);
    }
  }
}
