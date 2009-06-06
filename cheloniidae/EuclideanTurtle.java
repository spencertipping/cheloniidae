// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae;

import java.awt.Graphics2D;

public abstract class EuclideanTurtle extends Turtle implements EuclideanDriver {
  public EuclideanTurtle () {lineProvider = new QueueLineProvider ();}

  public abstract Vector direction ();

  public EuclideanTurtle line (Vector p1, Vector p2) {
    if (lineProvider != null) ((QueueLineProvider) lineProvider).add (new Line (p1, p2, lineSize, lineColor));
    if (listener     != null) listener.turtleProgress (this);
    return this;
  }

  public EuclideanTurtle move (double d) {
    Vector oldPosition = new Vector (position);
    this.line (oldPosition, position.addScaled (this.direction (), d));
    return this;
  }

  public EuclideanTurtle jump (double d) {
    position.addScaled (this.direction (), d);
    return this;
  }

  public EuclideanTurtle render (Graphics2D g, TurtleViewport viewport) {
    if (viewport != null) {
      Vector projectedPosition  = viewport.projectPoint (viewport.transformPoint (position));
      Vector projectedDirection = viewport.projectPoint (viewport.transformPoint (new Vector (position).addScaled (direction (), 10.0)));

      g.setColor (bodyColor ());
      g.drawOval ((int) projectedPosition.x - 2, (int) projectedPosition.y - 2, 4, 4);
      g.drawLine ((int) projectedPosition.x, (int) projectedPosition.y, (int) projectedDirection.x, (int) projectedDirection.y);
    }

    return this;
  }
}
