import cheloniidae.*;
import java.awt.*;

public class spirals {
  public static void main (String [] args) {
    StandardRotationalTurtle t1 = new StandardRotationalTurtle ();
    StandardRotationalTurtle t2 = new StandardRotationalTurtle ();
    StandardRotationalTurtle t3 = new StandardRotationalTurtle ();
    StandardRotationalTurtle t4 = new StandardRotationalTurtle ();
    TurtleWindow w = new TurtleWindow ();

    w.add (t1).add (t2).add (t3).add (t4).setVisible (true);

    t1.lineSize (0.125).lineColor (new Color (0.3f, 0.5f, 0.32f, 0.5f)).position (new Vector (141, 0, 0));
    t2.lineSize (0.125).lineColor (new Color (0.4f, 0.3f, 0.15f, 0.5f)).position (new Vector (-141, 0, 0));
    t3.lineSize (0.125).lineColor (new Color (0.2f, 0.3f, 0.60f, 0.5f)).position (new Vector (0, 141, 0));
    t4.lineSize (0.125).lineColor (new Color (0.5f, 0.4f, 0.15f, 0.5f)).position (new Vector (0, -141, 0));

    for (int i = 0; i < 2000; i++) {
      t1.move (Math.sqrt (i) * 1.5).pitch (60.1);
      t2.move (Math.sqrt (i) * 1.9).pitch (120.1);
      t3.move (Math.sqrt (i) * 1.7).pitch (90.1);
      if (i % 10 == 0) for (int j = 0; j < 60; ++j) t4.move (Math.sqrt (i) * 0.2).pitch (6.0);

      if (i % 6 == 0) t1.turn (140.2 / 60.0);
      if (i % 3 == 0) t2.turn (-140.2 / 120.0);
      if (i % 4 == 0) t3.bank (140.2 / 90.0);
      if (i % 10 == 0) t4.turn (4);

      if (i % 100 == 0) w.pause (100);
    }
  }
}
