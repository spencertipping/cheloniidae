import terrapin.*;

public class shading {
	public static void main (String[] args) {
		TurtleDrawingWindow w = new TurtleDrawingWindow ();
		Turtle t = new Turtle ();

		w.add (t);
		t.setPenColor (new java.awt.Color (0.3f, 0.5f, 0.9f, 0.5f));
		t.setPenSize (0.25);
		t.setPolarAxisModel (Turtle.Y_CYLINDRICAL);

		for (int i = 0; i < 36; i++) {
			for (int j = 0; j < 144; j++) {
				t.turnTheta (2.5);
				t.move (5);

				t.setPenSize (Math.abs (Math.cos (Math.PI / 180.0 * (t.getHeadingTheta () + t.getHeadingPhi ()))));
			}

			t.turnPhi (5);
		}

		w.setVisible (true);
	}
}
