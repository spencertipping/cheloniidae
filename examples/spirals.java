// vim: set ts=2 sw=2 :

import cheloniidae.*;
import java.awt.*;

public class spirals {
	public static void main (String [] args) {
		RotationalCartesianTurtle t1 = new RotationalCartesianTurtle ();
		RotationalCartesianTurtle t2 = new RotationalCartesianTurtle ();
		RotationalCartesianTurtle t3 = new RotationalCartesianTurtle ();
		TurtleDrawingWindow w = new TurtleDrawingWindow ();

		w.add (t1);
		w.add (t2);
		w.add (t3);
		w.setVisible (true);

		t1.lineSize (0.125).lineColor (new Color (0.3f, 0.5f, 0.32f, 0.5f)).position (new Vector (141, 0, 0));
		t2.lineSize (0.125).lineColor (new Color (0.4f, 0.3f, 0.15f, 0.5f)).position (new Vector (-141, 0, 0));
		t3.lineSize (0.125).lineColor (new Color (0.2f, 0.3f, 0.60f, 0.5f)).position (new Vector (0, 141, 0));
		
		for (int i = 0; i < 2000; i++) {
			t1.move (Math.sqrt (i) * 1.5).pitch (60.1);
			t2.move (Math.sqrt (i) * 1.9).pitch (120.1);
			t3.move (Math.sqrt (i) * 1.7).pitch (90.1);

      if (i % 6 == 0) t1.turn (140.2 / 60.0);
      if (i % 3 == 0) t2.turn (-140.2 / 120.0);
      if (i % 4 == 0) t3.bank (140.2 / 90.0);
		}
	}
}
