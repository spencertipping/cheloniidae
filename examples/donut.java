import terrapin.*;
import java.awt.*;

public class donut {
	public static void main (String[] args) {
		TurtleDrawingWindow w = new TurtleDrawingWindow ();
		YCylindricalTurtle t = new YCylindricalTurtle ();

		w.add (t);

		t.setDelayPerMove (0);
		t.setPenSize (0.5);
		t.setPenColor (new Color (0.5f, 0.65f, 0.55f, 0.5f));

		for (int i = 0; i < 36; i++) {
			t.jump (100);

			for (int j = 0; j < 90; j++) {
				t.move (2.0);
				t.turnTheta (4);
			}

			t.jump (-100);
			t.turnPhi (10);
		}
		
		w.setVisible (true);
	}
}
