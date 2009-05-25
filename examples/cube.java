// vim: set ts=2 sw=2 :

import cheloniidae.*;
import java.awt.*;

public class cube {
	public static void main (String [] args) {
		TurtleDrawingWindow w = new TurtleDrawingWindow ();
		Turtle t = new Turtle ();
		Axis a = new Axis (Axis.Z_AXIS | Axis.Y_AXIS | Axis.X_AXIS);

		w.add (t);
		w.add (a);
		t.setPenSize (0.5);
		t.setPenColor (Color.BLUE);
		t.setDelayPerMove (0);
		t.setVisible (false);

		for (int x = -4; x < 4; x++) {
			for (int y = -2; y < 2; y++) {
				for (int z = 3; z < 15; z++) {
					t.jumpTo (x * 100, y * 100, z * 100);
					t.turnPhi (0.5);
					cube (t, 50);
				}
			}
		}

		w.setVisible (true);
	}

	public static void cube (Turtle t, double distance) {
		int m = t.getPolarAxisModel ();
		t.setPolarAxisModel (Turtle.Y_CYLINDRICAL);
		for (int i = 0; i < 4; i++) {
			square3 (t, distance);
			t.jump (distance);
			t.turnPhi (90);
		}
		t.setPolarAxisModel (m);
	}

	public static void square3 (Turtle t, double distance) {
		for (int i = 0; i < 3; i++) {
			t.move (distance);
			t.turnTheta (90);
		}

		t.jump (distance);
		t.turnTheta (90);
	}
}
