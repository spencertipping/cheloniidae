package cheloniidae.commands;

import cheloniidae.Turtle;

public interface SupportsTurn<T implements Turtle> {
  public T turn (double angle);
}
