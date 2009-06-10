package cheloniidae.commands;

import cheloniidae.Turtle;

public interface SupportsBank<T implements Turtle> {
  public T bank (double angle);
}
