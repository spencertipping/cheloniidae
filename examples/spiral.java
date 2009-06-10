import cheloniidae.*;
import cheloniidae.commands.*;

public class spiral {
  public static void main (String[] args) {
    TurtleWindow              w = new TurtleWindow ();
    RotationalCartesianTurtle t = new RotationalCartesianTurtle ();

    t.lineSize (0.5);
    w.add (t).setVisible (true);

    TurtleCommand spiral = new Repeat (5760, new Pitch (0.25), new Move (10.0), new Pitch (-0.25), new Turn (4.0));
    t.run (spiral);
  }
}
