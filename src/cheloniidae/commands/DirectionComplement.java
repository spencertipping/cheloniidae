package cheloniidae.commands;

import cheloniidae.Turtle;
import cheloniidae.Vector;

public class DirectionComplement extends UnaryCommand<Vector> {
  public DirectionComplement (Vector value) {super (value);}
  public DirectionComplement applyTo (Turtle t) {
    if (t instanceof SupportsDirectionComplement) ((SupportsDirectionComplement) t).directionComplement (value);
    return this;
  }
}
