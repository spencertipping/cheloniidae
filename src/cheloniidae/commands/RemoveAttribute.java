package cheloniidae.commands;
import cheloniidae.*;
public class RemoveAttribute extends UnaryCommand<Attribute> {
  public RemoveAttribute (final Attribute _value) {super (_value);}
  public TurtleCommand applyTo (final Turtle t) {
    t.attributes ().remove (value);
    return this;
  }
}
