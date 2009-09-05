package cheloniidae.commands;

import cheloniidae.Turtle;

public class Visible extends UnaryCommand<Boolean> {
  public Visible (boolean value)        {super (value);}
  public Visible (Proxy<Boolean> value) {super (value);}
  public Visible applyTo (Turtle t) {
    if (t instanceof SupportsVisible) ((SupportsVisible) t).visible (super.value ());
    return this;
  }
}
