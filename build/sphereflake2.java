import cheloniidae.*;
import cheloniidae.frames.*;

import static cheloniidae.frames.CoreCommands.*;
import static java.lang.Math.sin;

public class sphereflake2 extends SingleTurtleScene {
  public static void main (String[] args) {new sphereflake2 ();}

  public TurtleCommand commands () {
    final int    majorStep = 16;
    final int    minorStep = 30;
    final double scale     = 20;
    final double radius    = scale * sin ((90 - 180 / minorStep) * Math.PI / 180.0) /
                                     sin ((360 / minorStep) * Math.PI / 180.0);

    final TurtleCommand sphere =
      bandSplitReplicator (turn (90 / majorStep),      // Run on the first turtle only.
                           pitch (-90), jump (radius), pitch (90),
                           triangleStart (),
                           size (0.5),
                           repeat (majorStep, turn (180 / majorStep),
                                              pitch (180 / minorStep),
                                              repeat (minorStep, jump (scale), triangleEmit (), pitch (360 / minorStep)),
                                              pitch (-180 / minorStep)),
                           visible (false));

    final TurtleCommand go = jump (radius * 3.0 / 2.0);
    final TurtleCommand recursiveStep = recurse ("sphereflake", 3, scale (1.0 / 2.0, true), visible (false));

    return recursiveBlock ("sphereflake",
                           color (0.5, 0.3, 0.2, 0.2),
                           sphere,
                           inductiveReplicator (4, turn (90), go, recursiveStep),
                           copy (pitch (-90), go, recursiveStep),
                           copy (pitch (90),  go, recursiveStep));
  }
}
