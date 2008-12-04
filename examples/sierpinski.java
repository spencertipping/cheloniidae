import terrapin.*;
import java.awt.*;

public class sierpinski {
	public static void main (String[] args) {
		TurtleDrawingWindow w = new TurtleDrawingWindow ();
		OrthogonalPlanarTurtle t = new OrthogonalPlanarTurtle ();

		w.add (t);

		t.setDelayPerMove (0);
		t.setPenSize (0.5);
		t.setPenColor (new Color (0.5f, 0.65f, 0.55f, 0.5f));

		t.jumpTo (-150.0, -150.0, 0.0);

		sierpinski_iteration (t, 300.0, 4);
		w.setVisible (true);
	}

	public static void sierpinski_iteration (OrthogonalPlanarTurtle t, double length, int recursionLevel) {
		//
		// Fill out the lines to make a 20-cube section of a Menger
		// sponge. We assume that we start at a corner, and then we return
		// the turtle to that position.
		//

		if (recursionLevel > 0) {
			for (int i = 0; i < 4; i++) {
				t.move (length / 3.0);

				for (int j = 0; j < 2; j++)
					sierpinski_iteration (t, length / 3.0, recursionLevel - 1);

				t.turnPhi (90.0);
			}

			t.turnTheta (90.0);
			t.move (length / 3.0);
			t.turnTheta (-90.0);

			for (int i = 0; i < 4; i++) {
				sierpinski_iteration (t, length / 3.0, recursionLevel - 1);
				t.jump (length / 1.5);
				t.turnPhi (90.0);
			}

			t.turnTheta (90.0);
			t.move (length / 1.5);
			t.turnTheta (90.0);
			t.turnPhi (180.0);

			for (int i = 0; i < 4; i++) {
				t.move (length / 3.0);

				for (int j = 0; j < 2; j++)
					sierpinski_iteration (t, length / 3.0, recursionLevel - 1);

				t.turnPhi (90.0);
			}

			t.turnTheta (90.0);
			t.jump (length);
			t.turnTheta (90.0);
			
			t.turnPhi (180.0);

			t.jump (length);
		} else
			t.jump (length);
	}
}
