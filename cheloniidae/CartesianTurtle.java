// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae;

public abstract class CartesianTurtle extends EuclideanTurtle {
  protected Vector direction;

  public Vector          direction ()                  {return direction;}
  public CartesianTurtle direction (Vector _direction) {direction = _direction; return this;}

  public final CartesianTurtle changeDirection (Vector delta) {
    direction.add (delta).normalize ();
    return this;
  }
}
