package cheloniidae.commands;

import cheloniidae.Turtle;
import java.awt.Color;

public interface SupportsLineColor<T extends Turtle> {
  public Color lineColor ();
  public T     lineColor (Color c);
}
