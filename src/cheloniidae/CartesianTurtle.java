// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae;

public abstract class CartesianTurtle<T implements Turtle> extends EuclideanTurtle<T> {
  protected Vector direction = new Vector (0, 1, 0);

  public Vector          direction ()                  {return new Vector (direction);}
  public CartesianTurtle direction (Vector _direction) {direction = _direction; return this;}

  public final CartesianTurtle changeDirection (Vector delta) {direction.add (delta).normalize ();
                                                               return this;}
}
