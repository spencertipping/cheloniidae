package cheloniidae;

import cheloniidae.commands.*;

import java.awt.Color;
import java.util.Set;

public abstract class RotationalCartesianTurtle<T extends RotationalCartesianTurtle> extends CartesianTurtle<T>
implements SupportsDirectionComplement<T>, SupportsPitch<T>, SupportsBank<T>, SupportsTurn<T> {

  public static class State extends CartesianTurtle.State {
    public final Vector directionComplement;

    public State (final Set<Attribute> _attributes, final Vector _position, final double _size, final Color _color,
                  final Vector _direction, final Vector _directionComplement) {
      super (_attributes, _position, _size, _color, _direction);
      directionComplement = _directionComplement.clone ();
    }

    public State applyTo (final Turtle t) {
      new DirectionComplement (directionComplement).applyTo (t);
      super.applyTo (t);
      return this;
    }
  }

  protected Vector directionComplement = new Vector (0, 0, -1);

  public Vector directionComplement ()                                  {return directionComplement;}
  public T      directionComplement (final Vector _directionComplement) {directionComplement = _directionComplement;
                                                                         return (T) this;}

  public T pitch (final double angle) {final Vector axis   = direction.cross (directionComplement);
                                       direction           = direction.rotatedAbout           (axis, angle);
                                       directionComplement = directionComplement.rotatedAbout (axis, angle);
                                       return (T) this;}
  public T bank (final double angle)
    {directionComplement = directionComplement.rotatedAbout (direction, angle); return (T) this;}
  public T turn (final double angle)
    {direction = direction.rotatedAbout (directionComplement, angle);           return (T) this;}

  public State serialize () {return new State (attributes, position, size, color, direction, directionComplement);}
  public String toString () {return super.toString () + ", direction complement = " +
                                    directionComplement ().toString ();}
}
