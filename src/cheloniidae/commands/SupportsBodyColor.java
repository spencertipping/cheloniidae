package cheloniidae.commands;

import cheloniidae.Turtle;
import java.awt.Color;

public interface SupportsBodyColor<T extends Turtle> {
  public Color bodyColor ();
  public T     bodyColor (Color c);
}
