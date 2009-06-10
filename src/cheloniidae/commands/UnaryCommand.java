package cheloniidae.commands;

import cheloniidae.TurtleCommand;

public class UnaryCommand<T> implements TurtleCommand {
  public final T value;
  public UnaryCommand (T _value) {value = _value;}
}
