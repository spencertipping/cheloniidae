package cheloniidae.commands;

import java.awt.Color;

public class BodyColor extends UnaryCommand<BodyColor, Color> {
  public BodyColor applyTo (Turtle t) {
    if (t instanceof SupportsBodyColor) ((SupportsBodyColor) t).bodyColor (value);
    return this;
  }
}
