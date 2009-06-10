package cheloniidae.commands;

import cheloniidae.Turtle;
import cheloniidae.Vector;

public class DirectionComplement extends UnaryCommand<DirectionComplement, Vector> {
  public Position applyTo (Turtle t) {
    if (t instanceof SupportsDirectionComplement) ((SupportsDirectionComplement) t).directionComplement (value);
    return this;
  }
}
