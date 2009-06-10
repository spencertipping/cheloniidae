package cheloniidae.commands;

import cheloniidae.Turtle;

public interface SupportsLineSize<T extends Turtle> {
  public T lineSize (double size);
}
