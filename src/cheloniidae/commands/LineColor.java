package cheloniidae.commands;

import cheloniidae.Turtle;
import java.awt.Color;

public class LineColor extends UnaryCommand<Color> {
  public LineColor (final Color value) {super (value);}
  public LineColor applyTo (final Turtle t) {
    if (t instanceof SupportsLineColor) ((SupportsLineColor) t).lineColor (value);
    return this;
  }
}
