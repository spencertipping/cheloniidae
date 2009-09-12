package cheloniidae.commands;

import cheloniidae.Turtle;
import cheloniidae.Vector;

public class DirectionComplement extends UnaryCommand<Vector> {
  public DirectionComplement (final Vector value) {super (value);}
  public DirectionComplement applyTo (final Turtle t) {
    if (t instanceof SupportsDirectionComplement) ((SupportsDirectionComplement) t).directionComplement (value);
    return this;
  }
}
