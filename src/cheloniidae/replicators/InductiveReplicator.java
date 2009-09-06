package cheloniidae.replicators;

import cheloniidae.*;

public class InductiveReplicator<T extends Turtle> implements Replicator<T> {
  public final TurtleCommand step;
  public final int           copies;
  public InductiveReplicator (int _copies, TurtleCommand _step) {step = _step; copies = _copies;}
  public TurtleGroup<T> replicate (T turtle) {
    TurtleGroup<T> result   = new TurtleGroup<T> ();
    T              previous = turtle;
    for (int i = 0; i < copies; ++i) result.turtles ().add (previous = (T) ((T) previous.clone ()).run (step));
    return result;
  }
}
