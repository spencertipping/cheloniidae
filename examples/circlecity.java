import terrapin.*;
import java.awt.*;

public class circlecity {
	public static void main (String[] args) {
		TurtleDrawingWindow w = new TurtleDrawingWindow ();
		Turtle t = new Turtle ();

		w.add (t);

		t.setPolarAxisModel (Turtle.Y_CYLINDRICAL);
		t.setDelayPerMove (0);
		t.setPenSize (0.5);
		t.setPenColor (new Color (0.5f, 0.65f, 0.55f, 0.5f));
		t.setVisible (false);

		iteration (t, 1500.0, 3);
		w.setVisible (true);
	}

	public static void iteration (Turtle t, double length, int recursionLevel) {
		final double length_divide = 4.0;

		if (recursionLevel > 0) {
			iteration (t, length / length_divide, recursionLevel - 1);

			for (int i = 0; i < 5; i++) {
				iteration (t, length / length_divide * 2.0, recursionLevel - 1);
				t.turnPhi (72.0);
				t.move (length / length_divide);
			}

			t.turnTheta (90.0);
			t.move (length / length_divide * 1.1);
			t.turnTheta (-90.0);

			for (int i = 0; i < 5; i++) {
				iteration (t, length / length_divide, 0);
				t.move (length / length_divide * 1.5);
				t.turnPhi (72.0);
			}

			t.turnTheta (90.0);
			t.move (length / length_divide * 1.1);
			t.turnTheta (90.0);

			iteration (t, length / length_divide, recursionLevel - 1);

			for (int i = 0; i < 5; i++) {
				iteration (t, length / length_divide * 2.0, recursionLevel - 1);
				t.turnPhi (72.0);
				t.move (length / length_divide);
			}

			t.turnTheta (90.0);
			t.move (length / length_divide * 1.1);
			t.turnTheta (90.0);

			t.move (length);
		} else
			t.move (length);
	}
}
