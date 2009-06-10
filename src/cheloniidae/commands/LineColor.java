package cheloniidae.commands;

import java.awt.Color;

public class LineColor extends UnaryCommand<LineColor, Color> {
  public LineColor applyTo (Turtle t) {
    if (t instanceof SupportsLineColor) ((SupportsLineColor) t).lineColor (value);
    return this;
  }
}
