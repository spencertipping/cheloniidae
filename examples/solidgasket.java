import cheloniidae.*;
import cheloniidae.frames.*;

import static cheloniidae.frames.CoreCommands.*;

// Yet another gasket attempt that I'm deferring to later.
// If anyone can fix it, I'd love to have the correct version.

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

    System.err.println ("\033[0;31mNote:\033[0;32m");
    System.err.println ("This scene doesn't work. After some hours of trying to find out why,");
    System.err.println ("I'd like to appeal to anyone who can find the bug(s). If you do end");
    System.err.println ("up with a proper Sierpinski gasket, you can e-mail it to me and I'll");
    System.err.println ("include it in the next release of Cheloniidae.\033[0;0m");

    return sequence (color (0.2, 0.3, 0.4, 0.1),
                     recursiveBlock ("gasket",
                       repeat (4,
                         repeat (2, recursiveStep, right, go, left, recursiveStep, right, pause (500)),
                         recursiveStep, turn (180), go, bank (90))));
  }
}
