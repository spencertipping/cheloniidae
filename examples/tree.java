import cheloniidae.*;
import cheloniidae.commands.*;
import cheloniidae.frames.*;

public class tree extends SingleTurtleScene {
  public static void main (String[] args) {
    new tree ();
  }

  public TurtleCommand run () {
    return tree (4, 20);
  }

  public CommandSequence tree (final int recursionLevel, final double scale) {
    return sequence (
      stack.push (),
      bank (random (360.0)),
      stack.push (),
      turn (random (20.0)),
      move (scale),
      (recursionLevel > 0) ? tree (recursionLevel - 1, scale * (randomNumber (0.4) + 0.5)) : pass (),
      stack.pop (),
      stack.push (),
      turn (random (-40.0)),
      move (scale),
      (recursionLevel > 0) ? tree (recursionLevel - 1, scale * (randomNumber (0.4) + 0.5)) : pass (),
      stack.pop (),
      stack.pop ());
  }
}
