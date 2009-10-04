package cheloniidae.commands;

import cheloniidae.TurtleCommand;

public class When extends TurtleCommand {
  public final Predicate<Turtle> predicate;
  public final TurtleCommand     action;

  public When (final Predicate<Turtle> _predicate, final TurtleCommand ... _actions)
    {predicate = _predicate; action = new Sequence (_actions);}

  public TurtleCommand applyTo (final Turtle t) {
    if (predicate.matches (t)) t.run (action);
    return this;
  }

  public TurtleCommand map (final Transformation<TurtleCommand> t) {
    final TurtleCommand newCommand = t.transform (this);
    if (newCommand == this) return new When (predicate, t.transform (action));
    else                    return newCommand;
  }
}
