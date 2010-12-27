import cheloniidae.*;
import cheloniidae.commands.*;
import cheloniidae.frames.*;

import static cheloniidae.frames.CoreCommands.*;

public class tree extends SingleTurtleScene {
  public static void main (String[] args) {
    new tree ();
  }

  public TurtleCommand commands () {
    return sequence (turn (180), tree (15, 100));
  }

  public Sequence tree (final int recursionLevel, final double scale) {
    return sequence (
      size (4),
      stack.push (),
      bank (random (360.0)),
      stack.push (),
      turn (random (20.0)),
      move (scale),
      (recursionLevel > 0) ? tree (recursionLevel - 1, scale * (random (0.2) + 0.8)) : pass (),
      stack.pop (),
      stack.push (),
      turn (random (-40.0)),
      move (scale),
      (recursionLevel > 0) ? tree (recursionLevel - 1, scale * (random (0.2) + 0.8)) : pass (),
      stack.pop (),
      stack.pop ());
  }
}
