package cheloniidae.commands;

import cheloniidae.Turtle;
import cheloniidae.TurtleCommand;
import cheloniidae.Transformation;

public class Repeat implements TurtleCommand, SerialTurtleCommandComposition {
  public final int             repetitions;
  public final CommandSequence body;

  public Repeat (final int _repetitions, final TurtleCommand ... _body)
    {repetitions = _repetitions; body = new CommandSequence (_body);}

  public Repeat applyTo (final Turtle t) {
    for (int i = 0; i < repetitions; ++i) body.applyTo (t);
    return this;
  }

  public TurtleCommand map (final Transformation<TurtleCommand> t) {
    final TurtleCommand newCommand = t.transform (this);
    if (newCommand == this) return new Repeat (repetitions, body.map (t));
    else                    return newCommand;
  }
}
