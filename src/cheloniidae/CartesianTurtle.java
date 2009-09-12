// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae;

import cheloniidae.commands.*;

import java.awt.Color;

public abstract class CartesianTurtle<T extends CartesianTurtle> extends EuclideanTurtle<T>
implements SupportsDirection<T> {

  public static class State extends EuclideanTurtle.State {
    public final Vector direction;
    
    public State (final Vector _position, final double _size, final Color _color, final Vector _direction) {
      super (_position, _size, _color);
      direction = _direction.clone ();
    }

    public State applyTo (final Turtle t) {
      new Direction (direction).applyTo (t);
      super.applyTo (t);
      return this;
    }
  }

  protected final Vector direction = new Vector (0, 1, 0);

  public Vector direction ()                        {return new Vector (direction);}
  public T      direction (final Vector _direction) {direction.assign (_direction); return (T) this;}

  public State serialize () {return new State (position, size, color, direction);}
}
