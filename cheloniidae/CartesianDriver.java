// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae;

public interface CartesianDriver extends EuclideanDriver {
  public Vector          direction ();
  public CartesianDriver direction (Vector _direction);
  public CartesianDriver changeDirection (Vector delta);
}
