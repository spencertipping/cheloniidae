import cheloniidae.*;
import cheloniidae.frames.*;

import java.awt.Color;

import static cheloniidae.frames.CoreCommands.*;

public class kochcurve extends DarkSingleTurtleScene {
  public static void main (final String[] args) {new kochcurve ();}

  public TurtleCommand commands () {
    final TurtleCommand recursiveInvocation = recurse ("curve", 5, scale (1.0 / 3.0), move (400));
    return repeat (3, size (0.125),
                   recursiveBlock ("curve",
                                   copy (color (new Color (0.5f, 0.6f, 0.7f, 0.3f)),
                                         turn (-30), move (400 * Math.sqrt (3)),
                                         turn (60),  move (400 * Math.sqrt (3)), visible (false)),
                                   copy (color (new Color (0.5f, 0.6f, 0.7f, 0.3f)), move (1200), visible (false)),

                                   recursiveInvocation, turn (-60),
                                   recursiveInvocation, turn (120),
                                   recursiveInvocation, turn (-60),
                                   recursiveInvocation),
                   turn (120));
  }
}
