// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae.commands;

public class Jump implements UnaryCommand<Jump, Double> {
  public Jump applyTo (Turtle t) {
    if (t instanceof SupportsJump) ((SupportsJump) t).jump (value);
    return this;
  }
}
