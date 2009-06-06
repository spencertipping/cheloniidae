// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae;

public interface RotationalCartesianDriver extends TurtleDriver {
  public Vector                    directionComplement ();
  public RotationalCartesianDriver directionComplement (Vector _directionComplement);

  public RotationalCartesianDriver pitch               (double angle);
  public RotationalCartesianDriver bank                (double angle);
  public RotationalCartesianDriver turn                (double angle);
  public RotationalCartesianDriver move                (double distance);
  public RotationalCartesianDriver jump                (double distance);
}
