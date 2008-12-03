// vim: set ts=2 sw=2 :

import terrapin.*;
import java.awt.*;

public class spheres {
	public static void main (String [] args) {
		TurtleDrawingWindow w = new TurtleDrawingWindow ();
		YCylindricalTurtle t = new YCylindricalTurtle ();
		AxialGrid g = new AxialGrid (AxialGrid.XY_PLANE | AxialGrid.XZ_PLANE);
		
		// Notice that we don't have the w.setVisible (true) line here.
		// Instead, we wait until the turtle is done before showing the
		// image. This speeds things up a lot because Java doesn't have
		// to refresh the window every time the turtle does something.
		
		w.add (t);
		w.add (g);
		t.setPenSize (0.5);
		t.setDelayPerMove (0);
		t.setVisible (false);
		t.setPenColor (new Color (0.3f, 0.5f, 0.5f, 0.5f));
		t.setBodyColor (new Color (0.3f, 0.5f, 0.5f));
		
		for (int i = 0; i < 35; i++) {
			zsphere (t, 8);
			t.turnTheta (3.0);
		}

		t.setHeadingTheta (0);
		t.jump (300);

		for (int i = 0; i < 35; i++) {
			tsphere (t, 7);
			t.turnPhi (5.0);
		}
		
		w.setVisible (true);
	}

	public static void tsphere (YCylindricalTurtle t, double step) {
		for (int i = 0; i < 72; i++) {
			t.move (step);
			t.turnTheta (5.0);
		}
	}
	
	public static void zsphere (YCylindricalTurtle t, double step) {
		for (int i = 0; i < 72; i++) {
			t.move (step);
			t.turnPhi (5.0);
		}
	}
}
