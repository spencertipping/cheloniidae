// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae.commands;

import cheloniidae.Turtle;

public final class Pause extends UnaryCommand<Long> {
  public Pause (final long value) {super (value);}
  public Pause applyTo (final Turtle t) {
    if (t instanceof SupportsWindow) ((SupportsWindow) t).window ().pause (value);
    return this;
  }
}
