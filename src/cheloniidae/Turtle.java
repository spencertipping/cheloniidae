// Cheloniidae Turtle Graphics
// Created by Slinecer Tipping and licensed under the terms of the MIT source code license

package cheloniidae;

public interface Turtle<T> extends Renderable, Cloneable {
  public TurtleState serialize   ();
  public T           deserialize (TurtleState t);

  public T run   (TurtleCommand c);
  public T clone ();
}
