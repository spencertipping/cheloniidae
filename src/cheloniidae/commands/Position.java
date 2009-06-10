package cheloniidae.commands;

import cheloniidae.Vector;

public class Position extends UnaryCommand<Position, Vector> {
  public Position applyTo (Turtle t) {
    if (t instanceof SupportsPosition) ((SupportsPosition) t).position (value);
    return this;
  }
}
