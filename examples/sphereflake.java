import cheloniidae.*;
import cheloniidae.frames.*;

import static cheloniidae.frames.CoreCommands.*;
import static java.lang.Math.sin;

public class sphereflake extends SingleTurtleScene {
  public static void main (String[] args) {new sphereflake ();}

  public TurtleCommand commands () {
    final int    majorStep = 20;
    final int    minorStep = 30;
    final double scale     = 20;
    final double radius    = scale * sin ((90 - 180 / minorStep) * Math.PI / 180.0) /
                                     sin ((360 / minorStep) * Math.PI / 180.0);

    final TurtleCommand sphere =
      bandSplitReplicator (turn (180 / majorStep),      // Run on the first turtle only.
                           pitch (-90), jump (radius), pitch (90),
                           triangleStart (),
                           repeat (majorStep, turn (180 / majorStep),
                                              pitch (180 / minorStep),
                                              repeat (minorStep, move (scale), triangleEmit (), pitch (360 / minorStep)),
                                              pitch (-180 / minorStep)));

    final TurtleCommand go = jump (radius * 4.0 / 3.0);
    final TurtleCommand recursiveStep = atomic (recurse ("sphereflake", 3, scale (1.0 / 3.0), visible (false)));

    return recursiveBlock ("sphereflake", sphere,
                           inductiveReplicator (6, turn (60),  go, recursiveStep),
                           turn (30),
                           inductiveReplicator (3, turn (120), pitch (-60), go, recursiveStep),
                           turn (-30),
                           inductiveReplicator (3, turn (120), pitch (60),  go, recursiveStep));
  }
}
