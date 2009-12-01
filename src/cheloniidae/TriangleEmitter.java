package cheloniidae;

public interface TriangleEmitter<T extends Turtle> {
  public static class Start extends NonDistributiveTurtleCommand {
    public Start () {}
    public TurtleCommand applyTo (final Turtle t)                        {if (t instanceof TriangleEmitter) ((TriangleEmitter) t).start (); return this;}
    public TurtleCommand map     (final Transformation<TurtleCommand> t) {return this;}
  }

  public static class Emit extends NonDistributiveTurtleCommand {
    public TurtleCommand applyTo (final Turtle t)                        {if (t instanceof TriangleEmitter) ((TriangleEmitter) t).emit (); return this;}
    public TurtleCommand map     (final Transformation<TurtleCommand> t) {return this;}
  }

  public T start ();
  public T emit ();
}
