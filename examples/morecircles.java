import cheloniidae.*;
import cheloniidae.frames.*;

public class morecircles extends SingleTurtleScene {
  public static void main (String[] args) {new morecircles ();}

  public TurtleCommand commands () {
    return recursiveBlock ("circlestack",
                           size (10), turn (1),
                           pitch (-90), move (750), pitch (90),
                           inductiveReplicator (3, sequence (pitch (-90), move (250), pitch (90), turn (3), visible (false)),
                                                repeat (6, move (500), turn (60),
                                                           recurse ("circlestack", 3, scale (1.0 / 3.0, true), pass ()))));
  }
}
