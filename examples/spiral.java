// vim: set ts=2 sw=2 :

import terrapin.*;
import java.awt.*;

public class spiral {
	public static void main (String [] args) {
		Turtle t1 = new Turtle ();
		Turtle t2 = new Turtle ();
		Turtle t3 = new Turtle ();
		TurtleDrawingWindow w = new TurtleDrawingWindow ();
		Axis a = new Axis (Axis.X_AXIS | Axis.Y_AXIS | Axis.Z_AXIS);

		w.add (t1);
		w.add (t2);
		w.add (t3);
		w.add (a);
		w.setVisible (true);
		
		t1.setPenSize (0.25);
		t1.setPenColor (new Color (0.3f, 0.5f, 0.32f, 0.5f));
		t1.setVisible (true);
		t1.setDelayPerMove (0);
		t1.setPolarAxisModel (Turtle.Y_CYLINDRICAL);

		t1.jumpTo (141, 0);

		t2.setPenSize (0.25);
		t2.setPenColor (new Color (0.4f, 0.3f, 0.15f, 0.5f));
		t2.setVisible (true);
		t2.setDelayPerMove (0);
		t2.setPolarAxisModel (Turtle.Y_CYLINDRICAL);

		t2.jumpTo (-141, 0);

		t3.setPenSize (0.25);
		t3.setPenColor (new Color (0.2f, 0.3f, 0.6f, 0.5f));
		t3.setVisible (true);
		t3.setDelayPerMove (0);
		t3.setPolarAxisModel (Turtle.Y_CYLINDRICAL);

		t3.jumpTo (0, 141);
		
		final int steps = 2000;
		for (int i = 0; i < steps; i++) {
			t1.move (Math.sqrt (i) * 2.5);
			t1.turn (60.1);
			t1.turnPhi (140.2 / 360.0);

			t2.move (Math.sqrt (i) * 3.5);
			t2.turn (120.1);
			t2.turnPhi (-140.2 / 360.0);

			t3.move (Math.sqrt (i) * 3.5);
			t3.turn (90.1);
			t3.turnPhi (140.2 / 90.0);
		}
	}
}
