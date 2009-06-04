// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae;

public class RotationalCartesianTurtle extends CartesianTurtle {
  protected Vector directionComplement = new Vector (0, 0, -1);

  public Vector                    directionComplement ()                            {return directionComplement;}
  public RotationalCartesianTurtle directionComplement (Vector _directionComplement) {directionComplement = _directionComplement; return this;}

  public RotationalCartesianTurtle pitch (double angle) {
    Vector axis = direction.cross (directionComplement);
    direction           = direction.rotateAbout           (axis, angle);
    directionComplement = directionComplement.rotateAbout (axis, angle);
    return this;
  }

  public RotationalCartesianTurtle bank (double angle) {
    directionComplement = directionComplement.rotateAbout (direction, angle);
    return this;
  }

  public RotationalCartesianTurtle turn (double angle) {
    direction = direction.rotateAbout (directionComplement, angle);
    return this;
  }
}
