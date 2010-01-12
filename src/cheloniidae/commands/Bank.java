package cheloniidae.commands;
import cheloniidae.Turtle;
public class Bank extends UnaryCommand<Double> {
  public Bank (final double value) {super (value);}
  public Bank applyTo (final Turtle t) {
    if (t instanceof SupportsBank) ((SupportsBank) t).bank (value);
    return this;
  }
}
