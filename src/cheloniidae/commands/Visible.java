package cheloniidae.commands;

import cheloniidae.Turtle;

public class Visible extends UnaryCommand<Visible, Boolean> {
  public Visible applyTo (Turtle t) {
    if (t instanceof SupportsVisible) ((SupportsVisible) t).visible (value);
    return this;
  }
}
