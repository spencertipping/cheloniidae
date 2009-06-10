package cheloniidae.commands;

import java.awt.Color;

public class BodyColor extends UnaryCommand<BodyColor, Color> {
  public BodyColor applyTo (Turtle t) {
    if (t instanceof HasBodyColor) ((HasBodyColor) t).bodyColor (value);
    return this;
  }
}
