// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae.commands;

import cheloniidae.Turtle;

public class Pitch extends UnaryCommand<Double> {
  public Pitch (double value) {super (value);}
  public Pitch applyTo (Turtle t) {
    if (t instanceof SupportsPitch) ((SupportsPitch) t).pitch (value);
    return this;
  }
}
