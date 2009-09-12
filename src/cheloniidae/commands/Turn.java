// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae.commands;

import cheloniidae.Turtle;

public class Turn extends UnaryCommand<Double> {
  public Turn (final double value) {super (value);}
  public Turn applyTo (final Turtle t) {
    if (t instanceof SupportsTurn) ((SupportsTurn) t).turn (value);
    return this;
  }
}
