package cheloniidae.commands;

import cheloniidae.Turtle;

public interface SupportsLineSize<T implements Turtle> {
  public T lineSize (double size);
}
