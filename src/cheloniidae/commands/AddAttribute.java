package cheloniidae.commands;

import cheloniidae.*;

public class AddAttribute extends AtomicCommand {
  public final Attribute attribute;
  public AddAttribute (final Attribute _attribute) {attribute = _attribute;}

  public TurtleCommand applyTo (final Turtle t) {
    t.attributes ().add (attribute);
    return this;
  }
}
