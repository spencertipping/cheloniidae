import cheloniidae.*;

public class square {
  public static void main (String[] args) {
    TurtleDrawingWindow       w = new TurtleDrawingWindow ();
    RotationalCartesianTurtle t = new RotationalCartesianTurtle ();
    w.add (t);
    w.setVisible (true);

    for (int i = 0; i < 4; ++i) {
      t.moveByDistance (100);
      t.turn (Math.PI / 2.0);
    }
  }
}
