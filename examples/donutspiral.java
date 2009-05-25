// vim: set ts=2 sw=2 :

import cheloniidae.*;
import java.awt.*;
import java.util.*;

public class donutspiral {
	public static void main (String [] args) {
		TurtleDrawingWindow w = new TurtleDrawingWindow ();
		AxialGrid a = new AxialGrid (AxialGrid.XZ_PLANE);
		Turtle t = new Turtle ();

		a.setGridStep (20.0);

		w.add (t);
		w.add (a);
		t.setPenSize (0.5);
		t.setDelayPerMove (0);

		t.setPolarAxisModel (Turtle.ORTHOGONAL_PLANAR);

		t.setPenColor (new Color (0.0f, 0.1f, 0.3f, 0.5f));

		t.jump (20.0);
		t.turnTheta (180.0);

		t.turnXi (45.0);

		for (int d = 0; d < 20; d++) {
			for (int i = 0; i < 36; i++) {
				t.turnTheta (20.0);
				circle (t, 2.0, 72, 5.0, true);
				t.turnTheta (-20.0);

				double leftover = 90.0 - t.getHeadingXi ();

				t.turnXi (leftover);
				circle (t, -2.0, 5, 5.0, false);
				t.turnXi (-leftover);
			}

			t.turnXi (4.5);

			if (t.getHeadingXi () > 90.0)
				t.setPenColor (new Color (0.0f, 0.1f, 0.3f, (float) ((135.0 - t.getHeadingXi ()) / 150.0)));
		}

		t.turnXi (-135.0);
		t.jump (300.0);
		t.turnXi (45.0);

		t.setPenColor (new Color (0.0f, 0.3f, 0.2f, 0.5f));

		for (int d = 0; d < 20; d++) {
			for (int i = 0; i < 36; i++) {
				t.turnTheta (20.0);
				circle (t, 1.0, 36, 10.0, true);
				t.turnTheta (-20.0);

				double leftover = 90.0 - t.getHeadingXi ();

				t.turnXi (leftover);
				circle (t, -1.0, 5, 5.0, false);
				t.turnXi (-leftover);
			}

			t.turnXi (4.5);

			if (t.getHeadingXi () > 90.0)
				t.setPenColor (new Color (0.0f, 0.3f, 0.2f, (float) ((135.0 - t.getHeadingXi ()) / 150.0)));
		}

		w.setVisible (true);
	}

	public static void circle (Turtle t, double step, int angle, double angle_step, boolean theta) {
		if (theta)
			t.turnTheta (angle_step / 2.0);
		else
			t.turnPhi (angle_step / 2.0);

		for (int i = 0; i < angle; i++) {
			t.move (step * angle_step);

			if (theta)
				t.turnTheta (angle_step);
			else
				t.turnPhi (angle_step);
		}

		if (theta)
			t.turnTheta (-angle_step / 2.0);
		else
			t.turnPhi (-angle_step / 2.0);
	}
}
