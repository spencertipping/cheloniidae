package cheloniidae.commands;

import cheloniidae.Turtle;
import cheloniidae.TurtleCommand;

public class NullCommand extends AtomicCommand {
  public NullCommand applyTo (final Turtle t) {return this;}
}
