import cheloniidae.*;
import cheloniidae.frames.*;

import static cheloniidae.frames.CoreCommands.*;
import static java.lang.Math.sin;

public class sphereflake extends SingleTurtleScene {
  public static void main (String[] args) {new sphereflake ();}

  public TurtleCommand commands () {
    final int    majorStep = 4;
    final int    minorStep = 6;
    final double scale     = 100;
    final double radius    = scale * sin ((90 - 180 / minorStep) * Math.PI / 180.0) /
                                     sin ((360 / minorStep) * Math.PI / 180.0);

    final TurtleCommand sphere =
      bandSplitReplicator (turn (180 / majorStep),      // Run on the first turtle only.
                           pitch (-90), jump (radius), pitch (90),
                           triangleStart (),
                           size (0.5),
                           repeat (majorStep, turn (180 / majorStep),
                                              pitch (180 / minorStep),
                                              repeat (minorStep, move (scale), triangleEmit (), pitch (360 / minorStep)),
                                              pitch (-180 / minorStep)),
                           visible (false));

    final TurtleCommand go = jump (radius * 4.0 / 3.0);
    final TurtleCommand recursiveStep = recurse ("sphereflake", 3, scale (1.0 / 3.0, true), visible (false));

    return recursiveBlock ("sphereflake",
                           color (0.5, 0.3, 0.2, 0.1),
                           sphere,
                           inductiveReplicator (6, turn (60),  go, recursiveStep),
                           inductiveReplicator (3, turn (120), bank (30), pitch (-60), bank (-30), go, recursiveStep),
                           inductiveReplicator (3, turn (120), bank (30), pitch (60),  bank (-30), go, recursiveStep));
  }
}
