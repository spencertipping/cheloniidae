// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae.commands;

import cheloniidae.Turtle;

public class Jump extends UnaryCommand<Double> {
  public Jump (double value)        {super (value);}
  public Jump (Proxy<Double> value) {super (value);}
  public Jump applyTo (Turtle t) {
    if (t instanceof SupportsJump) ((SupportsJump) t).jump (super.value ());
    return this;
  }
}
