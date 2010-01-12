package cheloniidae.replicators;

import cheloniidae.*;
import cheloniidae.commands.*;

public class BandSplitReplicator implements Replicator, TurtleCommand {
  public final TurtleCommand firstTurtlePrimer;
  public final Sequence      actions;

  public BandSplitReplicator (final TurtleCommand _firstTurtlePrimer, final TurtleCommand ... _actions)
    {firstTurtlePrimer = _firstTurtlePrimer; actions = new Sequence (_actions);}

  public TurtleGroup<Turtle> replicate (final Turtle turtle) {
    if (turtle instanceof EuclideanTurtle)
      // We have to wrap the pathwise connector in a new turtle group because of type annotations.
      // Specifying a generic with <EuclideanTurtle> makes it unassignable to anything specified
      // with <Turtle> because Java doesn't know whether the type will be in a contravariant position.
      // (In this case it isn't, but Java can't prove that.) The best we can do is wrap it.
      return new TurtleGroup<Turtle> (
               new PathwiseTriangleConnector<EuclideanTurtle> ()
                 .add ((EuclideanTurtle) turtle.clone ().run (firstTurtlePrimer))
                 .add ((EuclideanTurtle) turtle.clone ()));
    else return null;
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
