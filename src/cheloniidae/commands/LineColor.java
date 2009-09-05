package cheloniidae.commands;

import cheloniidae.Turtle;

import java.awt.Color;

public class LineColor extends UnaryCommand<Color> {
  public LineColor (Color value)        {super (value);}
  public LineColor (Proxy<Color> value) {super (value);}
  public LineColor applyTo (Turtle t) {
    if (t instanceof SupportsLineColor) ((SupportsLineColor) t).lineColor (super.value ());
    return this;
  }
}
