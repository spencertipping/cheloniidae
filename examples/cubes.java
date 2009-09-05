import cheloniidae.*;
import java.awt.*;

public class cubes {
  public static void main (String [] args) {
    TurtleWindow w = new TurtleWindow ();
    StandardRotationalTurtle t = new StandardRotationalTurtle ();

    w.add (t);
    t.lineColor (new Color (0.3f, 0.5f, 1.0f));

    for (int x = -4; x < 4; x++)
      for (int y = -2; y < 2; y++)
        for (int z = 3; z < 15; z++) {
          t.turn (0.5).pitch (0.5).position (new Vector (x * 100, y * 100, z * 100));
          cube (t, 50);
        }

    w.setVisible (true);
  }

  public static void cube (StandardRotationalTurtle t, double distance) {
    for (int i = 0; i < 4; i++) {
      square3 (t, distance);
      t.jump (distance).turn (90);
    }
  }

  public static void square3 (StandardRotationalTurtle t, double distance) {
    for (int i = 0; i < 3; i++) t.move (distance).pitch (90);
    t.jump (distance).pitch (90);
  }
}
