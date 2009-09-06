import cheloniidae.*;
import cheloniidae.replicators.*;
import cheloniidae.frames.*;

public class replicator extends SingleTurtleScene {
  public static void main (String[] args) {new replicator ();}

  public TurtleCommand run () {
    return replicate (new InductiveReplicator (20, sequence (pitch (90), jump (5), pitch (-90))),
                      replicate (new InductiveReplicator (90, sequence (turn (4), jump (2))),
                                 jump (10), move (100)));
  }
}
