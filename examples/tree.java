import terrapin.*;
import java.awt.Color;
import java.util.Random;

public class tree {
	public static final Color branches = new Color (0.3f, 0.25f, 0.2f, 0.8f);
	public static final Color leaves = new Color (0.0f, 0.2f, 0.0f, 0.5f);

	public static void main (String[] args) {
		TurtleDrawingWindow w = new TurtleDrawingWindow ();
		Turtle t = new Turtle ();
		w.add (t);
		t.turn (-90);
		t.jump (-100);
		tree (t, 60, 14, new Random ());
		w.setVisible (true);
	}

	public static void tree (Turtle t, double distance, int recursionLevel, Random r) {
		t.setPenColor (branches);
		t.setPenSize (distance * distance / 200.0);
		t.move (distance * (0.3 + 0.7 * r.nextDouble ()));
		t.turn (-15 * r.nextDouble () * 2.0);

		t.turnPhi ((r.nextDouble () - 0.5) * 60.0);

		if (recursionLevel > 0) {
			t.pushTurtleState ();
			tree (t, distance / 1.04 * (0.8 + 0.2 * r.nextDouble ()), recursionLevel - 1, r);
			t.popTurtleState ();
			t.turn (30 + r.nextDouble () * 4.0);
			t.pushTurtleState ();
			tree (t, distance / 1.04 * (0.8 + 0.2 * r.nextDouble ()), recursionLevel - 1, r);
			t.popTurtleState ();
		} else {
			t.jump (distance * -0.3);
			t.setPenSize (distance * distance / 300.0);
			t.setPenColor (leaves);

			t.turnPhi ((r.nextDouble () - 0.5) * 360.0);

			for (int i = 0; i < 4; i++) {
				t.pushTurtleState ();
				t.turn (-45 + (r.nextDouble () - 0.5) * 15.0);
				t.move (distance * 0.2 * (4 - i));
				t.popTurtleState ();
				t.pushTurtleState ();
				t.turn (45 + (r.nextDouble () - 0.5) * 15.0);
				t.move (distance * 0.2 * (4 - i));
				t.popTurtleState ();
				t.jump (distance * 0.3);
			}
		}
	}
}
