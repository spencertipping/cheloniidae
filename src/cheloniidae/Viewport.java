// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae;

public interface Viewport {
  public Graphics2D context        ();
  public Vector     transformPoint (Vector v);
  public Vector     projectPoint   (Vector v);
  public boolean    equals         (Viewport v);
}
