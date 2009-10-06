package cheloniidae.commands;

import cheloniidae.Turtle;
import cheloniidae.Vector;

public interface SupportsDirection<T extends Turtle> {
  public Vector direction ();
  public T      direction (Vector _direction);
}
