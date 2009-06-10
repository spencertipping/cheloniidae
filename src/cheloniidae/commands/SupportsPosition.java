package cheloniidae.commands;

import cheloniidae.Turtle;
import cheloniidae.Vector;

public interface SupportsPosition<T extends Turtle> {
  public T position (Vector _position);
}
