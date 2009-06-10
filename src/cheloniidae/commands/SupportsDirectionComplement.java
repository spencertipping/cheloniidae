package cheloniidae.commands;

import cheloniidae.Turtle;
import cheloniidae.Vector;

public interface SupportsDirectionComplement<T implements Turtle> {
  public T directionComplement (Vector _directionComplement);
}
