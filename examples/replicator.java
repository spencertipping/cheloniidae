import cheloniidae.*;
import cheloniidae.replicators.*;
import cheloniidae.frames.*;

public class replicator extends SingleTurtleScene {
  public static void main (String[] args) {new replicator ();}

  public TurtleCommand run () {
    return new InductiveReplicator<StandardRotationalTurtle>
                                  (360,
                                   sequence (turn (31), move (60), pitch (10)),
                                   jump (10), repeat (60, move (50), turn (73), pitch (0.5)));
  }
}
