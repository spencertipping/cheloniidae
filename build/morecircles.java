import cheloniidae.*;
import cheloniidae.frames.*;

import static cheloniidae.frames.CoreCommands.*;

public class morecircles extends SingleTurtleScene {
  public static void main (String[] args) {new morecircles ();}

  public TurtleCommand commands () {
    return recursiveBlock ("circlestack",
                           pitch (-90), size (40), move (750), size (10), pitch (90),
                           turn (1),
                           inductiveReplicator (3, sequence (pitch (-90), move (250), pitch (90), turn (1), visible (false)),
                                                repeat (6, move (500), turn (60), move (100),
                                                           recurse ("circlestack", 3, scale (1.0 / 3.0, true), pass ()))));
  }
}
