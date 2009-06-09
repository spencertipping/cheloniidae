// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae.commands;

public final class Move extends UnaryCommand<Double> {
  public Move applyTo (Turtle t) {
    if (t instanceof Moveable) ((Moveable) t).move (value);
    return this;
  }
}
