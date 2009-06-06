// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae;

public class RotationalCartesianTurtle extends CartesianTurtle implements RotationalCartesianDriver {
  public static class State implements TurtleState {
    public final Vector position;
    public final Vector direction;
    public final Vector directionComplement;
    public State (Vector _position, Vector _direction, Vector _directionComplement)
      {position = _position; direction = _direction; directionComplement = _directionComplement;}
  }

  protected Vector directionComplement = new Vector (0, 0, -1);

  public Vector                    directionComplement ()                            {return directionComplement;}
  public RotationalCartesianTurtle directionComplement (Vector _directionComplement) {directionComplement = _directionComplement; return this;}

  public RotationalCartesianTurtle pitch (double angle) {Vector axis         = direction.cross (directionComplement);
                                                         direction           = direction.rotateAbout           (axis, angle);
                                                         directionComplement = directionComplement.rotateAbout (axis, angle); return this;}

  public RotationalCartesianTurtle bank (double angle) {directionComplement = directionComplement.rotateAbout (direction, angle); return this;}
  public RotationalCartesianTurtle turn (double angle) {direction = direction.rotateAbout (directionComplement, angle); return this;}
  public RotationalCartesianTurtle move (double distance) {super.move (distance); return this;}
  public RotationalCartesianTurtle jump (double distance) {super.jump (distance); return this;}

  public State                     serialize   ()              {return new State (position, direction, directionComplement);}
  public RotationalCartesianTurtle deserialize (TurtleState s) {if (s instanceof State) {
                                                                  position            = ((State) s).position;
                                                                  direction           = ((State) s).direction;
                                                                  directionComplement = ((State) s).directionComplement;
                                                                }
                                                                return this;}
}
