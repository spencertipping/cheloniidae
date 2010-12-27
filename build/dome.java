import cheloniidae.*;
import cheloniidae.frames.*;

import static cheloniidae.frames.CoreCommands.*;

public class dome extends SingleTurtleScene {
  public static void main (String[] args) {new dome ();}

  public TurtleCommand commands () {
    return bandSplitReplicator (turn (6),
                                repeat (30, turn (6),
                                            pitch (3),
                                            repeat (60, move (4), triangleEmit (), pitch (6)),
                                            pitch (3)));
  }
}
