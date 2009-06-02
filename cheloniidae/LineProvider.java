// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae;

public interface LineProvider {
  public int  size ();
  public Line get  (int    index);
  public void sort (Vector center);
}
