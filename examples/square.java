import cheloniidae.*;

public class square {
  public static void main (String[] args) {
    TurtleDrawingWindow       w = new TurtleDrawingWindow ();
    RotationalCartesianTurtle t = new RotationalCartesianTurtle ();

    t.lineSize (0.5);
    w.add (t);
    w.setVisible (true);

    for (int i = 0; i < 5760; ++i) {
      t.pitch (Math.PI / 720.0).moveByDistance (10.0);
      t.pitch (-Math.PI / 720.0).turn (Math.PI / 90.0);
    }
  }
}
