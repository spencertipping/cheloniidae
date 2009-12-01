import cheloniidae.*;
import cheloniidae.frames.*;

import static cheloniidae.frames.CoreCommands.*;

public class solidgasket extends SingleTurtleScene {
  public static void main (String[] args) {new solidgasket ();}

  public TurtleCommand commands () {
    final TurtleCommand face = sequence (triangle (repeat (2, jump (100), turn (90)), jump (100)),
                                         triangle (repeat (2, jump (100), turn (90)), repeat (3, jump (100), turn (90))));

    final TurtleCommand cube = copy (repeat (4, face, jump (100), pitch (90)),
                                     turn (-90), pitch (90), face, pitch (-90), jump (-100), turn (90), jump (100),
                                     turn (90), pitch (90), face, visible (false));

    final TurtleCommand left  = turn (-90);
    final TurtleCommand right = turn (90);
    final TurtleCommand go    = jump (100);

    final TurtleCommand recursiveStep = recurse ("gasket", 1, scale (1.0 / 3.0), cube);

    return sequence (color (0.2, 0.3, 0.4, 0.1),
                     recursiveBlock ("gasket",
                       repeat (4,
                         repeat (2, recursiveStep, right, go, left, recursiveStep, right, pause (500)),
                         recursiveStep, turn (180), go, bank (90))));
  }
}
