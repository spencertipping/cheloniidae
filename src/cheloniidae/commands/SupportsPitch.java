package cheloniidae.commands;

import cheloniidae.Turtle;

public interface SupportsPitch<T implements Turtle> {
  public T pitch (double angle);
}
