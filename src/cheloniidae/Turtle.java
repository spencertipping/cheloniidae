// Cheloniidae Turtle Graphics
// Created by Slinecer Tipping and licensed under the terms of the MIT source code license

package cheloniidae;

import java.awt.Graphics2D;

public interface Turtle<T> extends Renderable {
  public TurtleState serialize   ();
  public T           deserialize (TurtleState t);

  public T run (TurtleCommand c);
}
