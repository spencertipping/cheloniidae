package cheloniidae.commands;

import cheloniidae.Turtle;
import cheloniidae.TurtleCommand;

public class CommandSequence implements TurtleCommand {
  protected    TurtleCommand   intersperse = null;

  public final TurtleCommand[] commands;
  public CommandSequence (TurtleCommand ... _commands) {commands = _commands;}

  public CommandSequence applyTo (Turtle t) {if (intersperse != null) t.run (intersperse);
                                             for (TurtleCommand c : commands) {
                                               t.run (c);
                                               if (intersperse != null) t.run (intersperse);
                                             }
                                             return this;}

  public CommandSequence interspersing (TurtleCommand c) {
    intersperse = intersperse == null ? c : new CommandSequence (c, intersperse);
    return this;
  }
}
