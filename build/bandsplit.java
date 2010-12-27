import cheloniidae.*;
import cheloniidae.frames.*;
import static cheloniidae.frames.CoreCommands.*;

public class bandsplit extends SingleTurtleScene {
  public static void main (String[] args) {new bandsplit();}

  public TurtleCommand commands () {
    return bandSplitReplicator (sequence (turn (90), move (10), turn (-90)),
                                repeat (30, move (10), triangleEmit (), pitch (12)));
  }
}
