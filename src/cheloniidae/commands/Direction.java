package cheloniidae.commands;

import cheloniidae.Turtle;
import cheloniidae.Vector;

public class Direction extends UnaryCommand<Vector> {
  public Direction (final Vector value) {super (value);}
  public Direction applyTo (final Turtle t) {
    if (t instanceof SupportsDirection) ((SupportsDirection) t).direction (value);
    return this;
  }
}
