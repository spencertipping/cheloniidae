// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae;

public interface Renderer<T implements Renderer> {
  public T render (Viewport v);
  public T render (Viewport v, double density);
}
