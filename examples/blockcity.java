import terrapin.*;
import java.awt.*;

// 
// This was my first attempt at a partial solution to create
// a Menger sponge. It produced such a cool result that
// I decided to make it a separate example file and tweak
// it.
//

public class blockcity {
	public static void main (String[] args) {
		TurtleDrawingWindow w = new TurtleDrawingWindow ();
		Turtle t = new Turtle ();

		w.add (t);

		t.setPolarAxisModel (Turtle.Y_CYLINDRICAL);
		t.setDelayPerMove (0);
		t.setPenSize (0.5);
		t.setPenColor (new Color (0.5f, 0.65f, 0.55f, 0.5f));
		t.setVisible (false);

		iteration (t, 1500.0, 4);
		w.setVisible (true);
	}

	public static void iteration (Turtle t, double length, int recursionLevel) {
		if (recursionLevel > 0) {
			iteration (t, length / 3.0, recursionLevel - 1);

			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 2; j++)
					iteration (t, length / 3.0, recursionLevel - 1);

				t.turnPhi (90.0);
				t.move (length / 3.0);
			}

			t.turnTheta (90.0);
			t.move (length / 2.8);
			t.turnTheta (-90.0);

			for (int i = 0; i < 4; i++) {
				iteration (t, length / 3.0, 0);
				t.move (length / 1.4);
				t.turnPhi (90.0);
			}

			t.turnTheta (90.0);
			t.move (length / 2.8);
			t.turnTheta (90.0);

			iteration (t, length / 3.0, recursionLevel - 1);

			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 2; j++)
					iteration (t, length / 3.0, recursionLevel - 1);

				t.turnPhi (90.0);
				t.move (length / 3.0);
			}

			t.turnTheta (90.0);
			t.move (length / 1.41);
			t.turnTheta (90.0);

			t.move (length);
		} else
			t.move (length);
	}
}
