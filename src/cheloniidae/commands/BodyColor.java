package cheloniidae.commands;

import cheloniidae.Turtle;
import java.awt.Color;

public class BodyColor extends UnaryCommand<Color> {
  public BodyColor (final Color value) {super (value);}
  public BodyColor applyTo (final Turtle t) {
    if (t instanceof SupportsBodyColor) ((SupportsBodyColor) t).bodyColor (value);
    return this;
  }
}
