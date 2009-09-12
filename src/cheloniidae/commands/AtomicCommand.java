package cheloniidae.commands;

import cheloniidae.Transformation;
import cheloniidae.TurtleCommand;

public abstract class AtomicCommand implements TurtleCommand {
  public TurtleCommand map (final Transformation<TurtleCommand> t) {return t.transform (this);}
}
