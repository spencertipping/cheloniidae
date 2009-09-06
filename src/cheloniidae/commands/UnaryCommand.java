package cheloniidae.commands;

import cheloniidae.TurtleCommand;

public abstract class UnaryCommand<T> implements TurtleCommand {
  public final T value;
  public UnaryCommand (T _value) {value = _value;}
}
