package cheloniidae;
public interface Turtle<T extends Turtle>
         extends Renderable, Cloneable, HasAttributes {
  public TurtleState serialize   ();
  public T           deserialize (TurtleState t);

  public T run   (TurtleCommand c);
  public T clone ();

  public TurtleWindow window ();
  public T            window (TurtleWindow _window);
}
