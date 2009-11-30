import cheloniidae.*;
import cheloniidae.frames.*;

import static cheloniidae.frames.CoreCommands.*;

public class brokengasket extends DarkSingleTurtleScene {
  public static void main (String[] args) {new brokengasket ();}

  public TurtleCommand commands () {
    TurtleCommand recursiveInvocation = recurse ("brokengasket", 7, scale (1.0 / 2.0, true), pass ());
    double scale = 500;
    return recursiveBlock ("brokengasket",
                           size (40),
                           recursiveInvocation, move (scale),
                           bank (-60), turn (120), move (scale), jump (-scale), turn (-120), bank (60), turn (120),
                           recursiveInvocation, move (scale),
                           bank (-60), turn (120), move (scale), jump (-scale), turn (-120), bank (60), turn (120),
                           recursiveInvocation, move (scale),
                           bank (-60), turn (120), move (scale), turn (120), recursiveInvocation, turn (-120),
                                                   jump (-scale), turn (-120), bank (60), turn (120));
  }
}
