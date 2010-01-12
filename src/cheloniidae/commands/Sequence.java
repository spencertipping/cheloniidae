package cheloniidae.commands;

import cheloniidae.SerialTurtleCommandComposition;
import cheloniidae.Turtle;
import cheloniidae.TurtleCommand;
import cheloniidae.Transformation;

public class Sequence implements TurtleCommand, SerialTurtleCommandComposition {
  protected TurtleCommand intersperse = null;

  public final TurtleCommand[] commands;
  public Sequence (final TurtleCommand ... _commands) {commands = _commands;}

  public Sequence applyTo (final Turtle t) {if (intersperse != null) t.run (intersperse);
                                            for (TurtleCommand c : commands) {
                                              t.run (c);
                                              if (intersperse != null) t.run (intersperse);
                                            }
                                            return this;}

  public Sequence interspersing (final TurtleCommand c) {
    intersperse = intersperse == null || c == null ? c : new Sequence (c, intersperse);
    return this;
  }

  public TurtleCommand map (final Transformation<TurtleCommand> t) {
    final TurtleCommand newCommand = t.transform (this);
    if (newCommand == this) {
      final TurtleCommand[] cs = new TurtleCommand[commands.length];
      for (int i = 0; i < commands.length; ++i) cs[i] = commands[i].map (t);
      return new Sequence (cs).interspersing (intersperse != null ? intersperse.map (t) : null);
    } else return newCommand;
  }
}
