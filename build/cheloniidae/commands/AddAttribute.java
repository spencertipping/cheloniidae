package cheloniidae.commands;
import cheloniidae.*;
public class AddAttribute extends UnaryCommand<Attribute> {
  public AddAttribute (final Attribute _value) {super (_value);}
  public TurtleCommand applyTo (final Turtle t) {
    t.attributes ().add (value);
    return this;
  }
}
