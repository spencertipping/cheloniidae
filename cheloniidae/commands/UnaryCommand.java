package cheloniidae.commands;

import cheloniidae.TurtleCommand;

public class UnaryCommand<T implements TurtleCommand, U> implements TurtleCommand<T> {
  public final U value;
  public UnaryCommand (U _value) {value = _value;}
}
