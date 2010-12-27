package cheloniidae.commands;
import cheloniidae.Turtle;
public class Jump extends UnaryCommand<Double> {
  public Jump (final double value) {super (value);}
  public Jump applyTo (final Turtle t) {
    if (t instanceof SupportsJump) ((SupportsJump) t).jump (value);
    return this;
  }
}
