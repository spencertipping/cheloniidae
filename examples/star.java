// vim: set ts=2 sw=2 :

import cheloniidae.*;
import java.awt.*;

public class star {
	public static void main (String [] args) {
		Turtle t = new Turtle ();
		TurtleDrawingWindow w = new TurtleDrawingWindow ();
		Axis a = new Axis (Axis.X_AXIS | Axis.Y_AXIS | Axis.Z_AXIS);

		w.add (t);
		w.add (a);
		w.setVisible (true);
		
		t.penSize (1.0);
		t.setDelayPerMove (10);
		t.setBodyColor (Color.BLACK);
		t.setVisible (true);
		t.setPolarAxisModel (Turtle.Z_SPHERICAL);
		
		final int steps = 400;
		for (int i = 0; i < steps; i++) {
			t.setPenColor (new Color (0.5f, 0.6f, 0.75f + (float) i / (float) (steps * 4), 1.0f - (float) i / (float) steps));
			t.move (i);
			t.turn (149.95);
			t.turnPhi (0.00125);
		}
	}
}
