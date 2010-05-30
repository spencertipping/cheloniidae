package cheloniidae.commands;
import cheloniidae.Turtle;
public final class Move extends UnaryCommand<Double> {
  public Move (final double value) {super (value);}
  public Move applyTo (final Turtle t) {
    if (t instanceof SupportsMove) ((SupportsMove) t).move (value);
    return this;
  }
}
