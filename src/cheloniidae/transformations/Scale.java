package cheloniidae.transformations;

import cheloniidae.*;
import cheloniidae.commands.*;

public class Scale implements Transformation<TurtleCommand> {
  public final double  factor;
  public final boolean scaleLineSizes;

  public Scale (double _factor)                          {this (_factor, false);}
  public Scale (double _factor, boolean _scaleLineSizes) {factor = _factor; scaleLineSizes = _scaleLineSizes;}

  public TurtleCommand transform (TurtleCommand c) {
    if                        (c instanceof Move)     return new Move     (((Move) c).value     * factor);
    else if                   (c instanceof Jump)     return new Jump     (((Jump) c).value     * factor);
    else if (scaleLineSizes && c instanceof LineSize) return new LineSize (((LineSize) c).value * factor);
    else                                              return c;
  }
}
