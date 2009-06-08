import cheloniidae.*;

public class spiral {
  public static void main (String[] args) {
    TurtleDrawingWindow       w = new TurtleDrawingWindow ();
    RotationalCartesianTurtle t = new RotationalCartesianTurtle ();

    t.lineSize (0.5);
    w.add (t);
    w.setVisible (true);

    for (int i = 0; i < 5760; ++i) t.pitch (0.25).move (10.0).pitch (-0.25).turn (4.0);
  }
}
