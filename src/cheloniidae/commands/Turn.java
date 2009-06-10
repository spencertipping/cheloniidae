// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae.commands;

public class Turn extends UnaryCommand<Turn, Double> {
  public Turn applyTo (Turtle t) {
    if (t instanceof SupportsTurn) ((SupportsTurn) t).turn (value);
    return this;
  }
}
