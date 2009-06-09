package cheloniidae;

import java.awt.Color;

public interface SupportsBodyColor<T implements Turtle> {
  public Color bodyColor ();
  public T     bodyColor (Color c);
}
