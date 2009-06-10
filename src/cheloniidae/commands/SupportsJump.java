package cheloniidae.commands;

import cheloniidae.Turtle;

public interface SupportsJump<T implements Turtle> {
  public T jump (double distance);
}
