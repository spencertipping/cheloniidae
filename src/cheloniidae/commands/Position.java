package cheloniidae.commands;

import cheloniidae.Turtle;
import cheloniidae.Vector;

public class Position extends UnaryCommand<Vector> {
  public Position (final Vector value) {super (value);}
  public Position applyTo (final Turtle t) {
    if (t instanceof SupportsPosition) ((SupportsPosition) t).position (value);
    return this;
  }
}
