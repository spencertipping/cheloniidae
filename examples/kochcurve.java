// vim: set ts=2 sw=2 :

import cheloniidae.*;
import java.awt.*;

public class kochcurve {
	public static void main (String [] args) {
		TurtleDrawingWindow       w = new TurtleDrawingWindow ();
		RotationalCartesianTurtle t = new RotationalCartesianTurtle ();

		w.add (t).setVisible (true);
		t.lineSize (0.5).lineColor (Color.BLUE);

		for (int i = 0; i < 3; i++) {
			curve (t, 400, 5);
			t.turn (120);
		}
	}

	public static void curve (RotationalCartesianTurtle t, double distance, int recursionlevel) {
		if (recursionlevel > 0) {
			curve (t, distance / 3.0, recursionlevel - 1);
			t.turn (-60);
			curve (t, distance / 3.0, recursionlevel - 1);
			t.turn (120);
			curve (t, distance / 3.0, recursionlevel - 1);
			t.turn (-60);
			curve (t, distance / 3.0, recursionlevel - 1);
		} else t.move (distance);
	}
}
