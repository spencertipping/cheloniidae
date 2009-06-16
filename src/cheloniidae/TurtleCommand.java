// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae;

import java.io.Serializable;

public interface TurtleCommand extends Serializable {
  public TurtleCommand applyTo (Turtle t);
}