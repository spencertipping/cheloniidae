// vim: set ts=2 sw=2 :

import cheloniidae.*;
import java.awt.*;

public class spheres {
	public static void main (String [] args) {
		TurtleDrawingWindow       w = new TurtleDrawingWindow ();
		RotationalCartesianTurtle t = new RotationalCartesianTurtle ();
		
		w.add (t);
		w.setVisible (true);

		t.lineSize (0.1).lineColor (new Color (0.3f, 0.5f, 0.5f, 0.5f)).bodyColor (new Color (0.3f, 0.5f, 0.5f));
		
    for (int i = 0; i < 10; ++i) {
      for (int j = 0; j < 35; ++j) {
        zsphere (t, 8 - (i * 0.5));
        t.turn (3);
      }
      t.turn (-30).bank (8);
    }
	}

	public static void zsphere (RotationalCartesianTurtle t, double step) {
		for (int i = 0; i < 72; i++) {
			t.move (step);
			t.pitch (5);
		}
	}
}
