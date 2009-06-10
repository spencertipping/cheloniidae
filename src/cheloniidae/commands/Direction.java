package cheloniidae.commands;

import cheloniidae.Turtle;
import cheloniidae.Vector;

public class Direction extends UnaryCommand<Direction, Vector> {
  public Position applyTo (Turtle t) {
    if (t instanceof SupportsDirection) ((SupportsDirection) t).direction (value);
    return this;
  }
}
