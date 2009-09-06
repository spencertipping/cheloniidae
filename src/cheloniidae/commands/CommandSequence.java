package cheloniidae.commands;

import cheloniidae.Turtle;
import cheloniidae.TurtleCommand;

public class CommandSequence implements TurtleCommand {
  public final TurtleCommand[] commands;
  public CommandSequence (TurtleCommand ... _commands) {commands = _commands;}

  public CommandSequence applyTo (Turtle t) {for (TurtleCommand c : commands) c.applyTo (t);
                                             return this;}
}
