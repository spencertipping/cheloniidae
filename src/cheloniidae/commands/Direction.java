package cheloniidae.commands;

import cheloniidae.Turtle;
import cheloniidae.Vector;

public class Direction extends UnaryCommand<Vector> {
  public Direction (Vector value)        {super (value);}
  public Direction (Proxy<Vector> value) {super (value);}
  public Direction applyTo (Turtle t) {
    if (t instanceof SupportsDirection) ((SupportsDirection) t).direction (super.value ());
    return this;
  }
}
