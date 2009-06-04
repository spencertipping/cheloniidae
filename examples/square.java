import cheloniidae.*;

public class square {
  public static void main (String[] args) {
    TurtleDrawingWindow       w = new TurtleDrawingWindow ();
    RotationalCartesianTurtle t = new RotationalCartesianTurtle ();
    w.add (t);
    w.setVisible (true);

    for (int i = 0; i < 360; ++i) {
      t.moveByDistance (1);
      t.turn (Math.PI / 180.0);
    }
  }
}
