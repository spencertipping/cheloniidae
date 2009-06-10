package cheloniidae.commands;

import cheloniidae.Turtle;

public interface SupportsMove<T implements Turtle> {
  public T move (double distance);
}
