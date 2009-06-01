// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae;

public abstract class EuclideanTurtle extends Turtle {
  protected abstract Vector direction ();

  protected final EuclideanTurtle moveByDistance (double d) {
    Vector oldPosition = new Vector (position);
    return super.line (oldPosition, position.addScaled (direction (), d));
  }
}
