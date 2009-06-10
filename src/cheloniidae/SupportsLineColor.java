package cheloniidae;

import java.awt.Color;

public interface SupportsLineColor<T implements Turtle> {
  public Color lineColor ();
  public T     lineColor (Color c);
}
