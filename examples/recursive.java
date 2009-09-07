import cheloniidae.*;
import cheloniidae.commands.*;
import cheloniidae.frames.*;

public class recursive extends SingleTurtleScene {
  public static void main (String[] args) {
    new recursive ();
  }

  public TurtleCommand commands () {
    return sequence (turn (180), size (4),
                     recursiveBlock ("tree", stack.push (),
                                             bank (random (360.0)),
                                             stack.push (),
                                             turn (random (20.0)),
                                             move (100),
                                             recurse ("tree", 10, scale (0.8), pass ()),
                                             stack.pop (),
                                             stack.push (),
                                             turn (random (-40.0)),
                                             move (100),
                                             recurse ("tree", 8, scale (0.7), pass ()),
                                             stack.pop (),
                                             stack.pop ()));
  }
}
