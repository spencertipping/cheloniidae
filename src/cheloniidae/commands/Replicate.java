package cheloniidae.commands;

import cheloniidae.*;

public class Replicate implements NonDistributiveTurtleCommand {
  public final Replicator      replicator;
  public final CommandSequence actions;

  public Replicate (Replicator _replicator, TurtleCommand ... _actions)
    {replicator = _replicator; actions = new CommandSequence (_actions);}

  public TurtleCommand applyTo (Turtle t) {
    // Nothing is done with the active turtle. We merely use it as a basis for replication and modify the copies.
    TurtleGroup g = t.replicate (replicator);
    t.window ().add (g);
    return actions.applyTo (g);
  }
}
