// vim: set ts=2 sw=2 :

import cheloniidae.*;
import java.awt.*;

public class kochcurve {
	public static void main (String [] args) {
		TurtleDrawingWindow w = new TurtleDrawingWindow ();
		Turtle t = new Turtle ();
		AxialGrid grid = new AxialGrid (AxialGrid.XY_PLANE);
		Axis z = new Axis (Axis.Z_AXIS);
		
		w.add (t);
		w.add (grid);
		w.add (z);
		t.setPenSize (0.5);
		t.setPenColor (Color.BLUE);
		t.setDelayPerMove (5);
		t.setVisible (true);
		w.setVisible (true);

		for (int i = 0; i < 3; i++) {
			curve (t, 400, 5);
			t.turn (120);
		}
	}

	public static void curve (Turtle t, double distance, int recursionlevel) {
		if (recursionlevel > 0) {
			curve (t, distance / 3.0, recursionlevel - 1);
			t.turn (-60);
			curve (t, distance / 3.0, recursionlevel - 1);
			t.turn (120);
			curve (t, distance / 3.0, recursionlevel - 1);
			t.turn (-60);
			curve (t, distance / 3.0, recursionlevel - 1);
		} else {
			t.move (distance);
		}
	}
}
