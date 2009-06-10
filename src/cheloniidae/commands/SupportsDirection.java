package cheloniidae.commands;

import cheloniidae.Turtle;
import cheloniidae.Vector;

public interface SupportsDirection<T implements Turtle> {
  public T direction (Vector _direction);
}
