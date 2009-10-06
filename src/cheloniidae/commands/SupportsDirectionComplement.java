package cheloniidae.commands;

import cheloniidae.Turtle;
import cheloniidae.Vector;

public interface SupportsDirectionComplement<T extends Turtle> {
  public Vector directionComplement ();
  public T      directionComplement (Vector _directionComplement);
}
