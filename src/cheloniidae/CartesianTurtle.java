// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae;

import cheloniidae.commands.*;

import java.awt.Color;

public abstract class CartesianTurtle<T extends CartesianTurtle> extends EuclideanTurtle<T>
implements SupportsDirection<T> {

  public static class State extends EuclideanTurtle.State {
    public final Vector direction;
    
    public State (Vector _position, double _size, Color _color, Vector _direction) {
      super (_position, _size, _color);
      direction = _direction;
    }

    public State applyTo (Turtle t) {
      new Direction (direction).applyTo (t);
      super.applyTo (t);
      return this;
    }
  }

  protected Vector direction = new Vector (0, 1, 0);

  public Vector direction ()                  {return new Vector (direction);}
  public T      direction (Vector _direction) {direction = _direction; return (T) this;}
}
