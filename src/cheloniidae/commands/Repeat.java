package cheloniidae.commands;

import cheloniidae.Turtle;
import cheloniidae.TurtleCommand;

public class Repeat implements TurtleCommand {
  public final int             repetitions;
  public final TurtleCommand[] body;

  public Repeat (int _repetitions, TurtleCommand ... _body)
    {repetitions = _repetitions; body = _body;}

  public Repeat applyTo (Turtle t) {
    for (int i = 0; i < repetitions; ++i)
      for (int j = 0; j < body.length; ++j) body[j].applyTo (t);
    return this;
  }
}
