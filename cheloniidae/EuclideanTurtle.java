// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae;

public abstract class EuclideanTurtle extends Turtle {
  public EuclideanTurtle () {provider = new QueueLineProvider ();}

  protected abstract Vector direction ();

  protected final Turtle line (Vector p1, Vector p2) {
    if (lineProvider != null) lines.add (new Line (p1, p2, lineSize, lineColor));
    if (listener     != null) listener.turtleProgress (this);
    return this;
  }

  protected final EuclideanTurtle moveByDistance (double d) {
    Vector oldPosition = new Vector (position);
    return this.line (oldPosition, position.addScaled (this.direction (), d));
  }

  protected final EuclideanTurtle jumpByDistance (double d) {
    position.addScaled (this.direction (), d);
    return this;
  }
}
