import cheloniidae.*;
import cheloniidae.commands.*;

import java.awt.Color;

public class circles {
  public static void main (String[] args) {
    TurtleWindow              w = new TurtleWindow ();
    RotationalCartesianTurtle t = new RotationalCartesianTurtle ();

    w.add (t).setVisible (true);
    t.run (section (500, 3));
  }

  public static TurtleCommand section (double scale, int recursionLevel) {
    return new Repeat (15,
      new CommandSequence (
        new Move (scale),
        new Turn ((recursionLevel & 1) == 0 ? 36 : -36),
        new Pitch (1),
        (recursionLevel > 0) ? section (scale / 3.0, recursionLevel - 1) : new NullCommand ()));
  }
}
