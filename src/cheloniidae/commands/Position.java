package cheloniidae.commands;

import cheloniidae.Turtle;
import cheloniidae.Vector;

public class Position extends UnaryCommand<Vector> {
  public Position (Vector value) {super (value);}
  public Position applyTo (Turtle t) {
    if (t instanceof SupportsPosition) ((SupportsPosition) t).position (value);
    return this;
  }
}
