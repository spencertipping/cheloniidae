import cheloniidae.*;
import java.awt.*;

public class polyhedron {
  public static void main (String[] args) {
    TurtleDrawingWindow w = new TurtleDrawingWindow ();
    Turtle t = new Turtle ();

    w.add (t);

    t.setPolarAxisModel (Turtle.ORTHOGONAL_PLANAR);
    t.setDelayPerMove (0);
    t.setPenSize (0.5);
    t.setPenColor (new Color (0.5f, 0.65f, 0.55f, 0.5f));
    t.setVisible (false);

    iteration (t, 1500.0, 7);
    w.setVisible (true);
  }

  public static void iteration (Turtle t, double length, int recursionLevel) {
    if (recursionLevel <= 0) {
      t.move (length * 3.0);
    } else {
      iteration (t, length / 3.0, recursionLevel - 1);
      t.turnPhi (90);
      iteration (t, length / 3.0, recursionLevel - 1);
      t.turnTheta (90);
      iteration (t, length / 3.0, recursionLevel - 1);
      t.turnXi (90);
      iteration (t, length / 3.0, recursionLevel - 1);
      t.turnPhi (-90);
      t.turnTheta (90);
      iteration (t, length / 3.0, recursionLevel - 1);
      t.turnTheta (90);
      t.turnPhi (-90);
      t.turnXi (-90);
    }
  }
}
