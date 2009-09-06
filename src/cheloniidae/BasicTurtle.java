package cheloniidae;

public abstract class BasicTurtle<T extends BasicTurtle> extends Replicable<T> implements Turtle<T> {
  protected TurtleWindow window = null;

  public TurtleWindow window ()                     {return window;}
  public T            window (TurtleWindow _window) {window = _window; return (T) this;}

  public T run (TurtleCommand c) {
    c.applyTo (this);
    return (T) this;
  }

  public TurtleCommand map (Transformation<TurtleCommand> t) {
    TurtleState serialized = this.serialize ();
    if (serialized instanceof TurtleCommand) return ((TurtleCommand) serialized).map (t);
    else                                     return null;
  }
}
