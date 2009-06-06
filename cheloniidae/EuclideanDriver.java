// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae;

public interface EuclideanDriver extends TurtleDriver {
  public EuclideanDriver line (Vector v1, Vector v2);
  public EuclideanDriver move (double distance);
  public EuclideanDriver jump (double distance);
}
