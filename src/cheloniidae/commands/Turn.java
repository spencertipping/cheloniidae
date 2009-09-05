// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae.commands;

import cheloniidae.Turtle;

public class Turn extends UnaryCommand<Double> {
  public Turn (double value)        {super (value);}
  public Turn (Proxy<Double> value) {super (value);}
  public Turn applyTo (Turtle t) {
    if (t instanceof SupportsTurn) ((SupportsTurn) t).turn (super.value ());
    return this;
  }
}
