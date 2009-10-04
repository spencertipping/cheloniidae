package cheloniidae.commands;

import cheloniidae.*;

public class RemoveAttribute extends AtomicCommand {
  public final Attribute attribute;
  public RemoveAttribute (final Attribute _attribute) {attribute = _attribute;}

  public TurtleCommand applyTo (final Turtle t) {
    t.attributes ().remove (t);
    return this;
  }
}
