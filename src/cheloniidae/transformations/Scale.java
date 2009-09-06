package cheloniidae.transformations;

import cheloniidae.*;
import cheloniidae.commands.*;

public class Scale implements Transformation<TurtleCommand> {
  public final double factor;
  public Scale (double _factor) {factor = _factor;}

  public TurtleCommand transform (TurtleCommand c) {
    if      (c instanceof Move) return new Move (((Move) c).value * factor);
    else if (c instanceof Jump) return new Jump (((Jump) c).value * factor);
    else                        return c;
  }
}
