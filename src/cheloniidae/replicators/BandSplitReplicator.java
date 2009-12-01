package cheloniidae.replicators;

import cheloniidae.*;
import cheloniidae.commands.*;

public class BandSplitReplicator extends Replicator {
  public final Sequence firstTurtlePrimer;
  public final Sequence actions;

  public InductiveReplicator (final Sequence _firstTurtlePrimer, final TurtleCommand ... _actions)
    {firstTurtlePrimer = _firstTurtlePrimer; actions = new Sequence (_actions);}

  public TurtleGroup<Turtle> replicate (final Turtle turtle) {
    return new PathwiseTriangleConnector<Turtle> ().add (turtle.clone ().run (firstTurtlePrimer)).add (turtle.clone ());
  }

  public TurtleCommand applyTo (final Turtle turtle) {
    final TurtleGroup<Turtle> copies = this.replicate (turtle);
    turtle.window ().add (copies);      // Very important! Otherwise the copies will have no way of producing visible lines.
    copies.run (actions);
    return this;
  }

  public TurtleCommand map (final Transformation<TurtleCommand> t) {
    final TurtleCommand newCommand = t.transform (this);
    if (newCommand == this) return new BandSplitReplicator (firstTurtlePrimer.map (t), actions.map (t));
    else                    return newCommand;
  }
}
