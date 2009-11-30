import cheloniidae.*;
import cheloniidae.replicators.*;
import cheloniidae.frames.*;

import static cheloniidae.frames.CoreCommands.*;

public class replicatedtube extends SingleTurtleScene {
  public static void main (String[] args) {new replicatedtube ();}

  public TurtleCommand commands () {
    return inductiveReplicator (120, sequence (turn (61), move (50), pitch (10)),
                                jump (10), repeat (60, move (50), turn (72.2), pitch (0.5)));
  }
}
