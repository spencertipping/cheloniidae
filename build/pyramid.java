import cheloniidae.*;
import cheloniidae.frames.*;
import cheloniidae.commands.*;

import static cheloniidae.frames.CoreCommands.*;

public class pyramid extends SingleTurtleScene {
  public static void main (String[] args) {new pyramid ();}

  public TurtleCommand commands () {
    final StandardRotationalTurtle c1 = new StandardRotationalTurtle ();
    final StandardRotationalTurtle c2 = new StandardRotationalTurtle ().move (100);
    final StandardRotationalTurtle c3 = new StandardRotationalTurtle ().turn (60).move (100);
    return sequence (triangle ((SupportsPosition) c2, c3),
                     turn (30), pitch (-60), move (100), triangle ((SupportsPosition) c1, c2),
                                                         triangle ((SupportsPosition) c2, c3),
                                                         triangle ((SupportsPosition) c1, c3));
  }
}
