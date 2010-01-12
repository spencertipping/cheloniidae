package cheloniidae.commands;
import cheloniidae.Turtle;
public class Pitch extends UnaryCommand<Double> {
  public Pitch (final double value) {super (value);}
  public Pitch applyTo (final Turtle t) {
    if (t instanceof SupportsPitch) ((SupportsPitch) t).pitch (value);
    return this;
  }
}
