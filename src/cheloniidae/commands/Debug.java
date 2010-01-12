package cheloniidae.commands;
import cheloniidae.Turtle;
public class Debug extends UnaryCommand<String> {
  public Debug (final String value) {super (value);}
  public Debug applyTo (final Turtle t) {
    System.err.println (value + " " + t.toString ());
    return this;
  }
}
