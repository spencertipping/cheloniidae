// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae.commands;

import cheloniidae.Turtle;

public class Bank extends UnaryCommand<Double> {
  public Bank (double value) {super (value);}
  public Bank applyTo (Turtle t) {
    if (t instanceof SupportsBank) ((SupportsBank) t).bank (value);
    return this;
  }
}
