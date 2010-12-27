import cheloniidae.*;
import cheloniidae.commands.*;
import cheloniidae.frames.*;

import static cheloniidae.frames.CoreCommands.*;

public class recursive extends SingleTurtleScene {
  public static void main (String[] args) {
    new recursive ();
  }

  public TurtleCommand commands () {
    return sequence (turn (180),
                     recursiveBlock ("tree", stack.push (),
                                             size (4),
                                             bank (random (360.0)),
                                             stack.push (),
                                             turn (random (20.0)),
                                             move (100),
                                             recurse ("tree", 16, scale (0.8, true), pass ()),
                                             stack.pop (),
                                             stack.push (),
                                             turn (random (-40.0)),
                                             move (100),
                                             recurse ("tree", 16, scale (0.7, true), pass ()),
                                             stack.pop (),
                                             stack.pop ()));
  }
}
