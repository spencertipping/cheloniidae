package cheloniidae;

import cheloniidae.commands.AtomicCommand;

public interface TriangleEmitter<T extends Turtle> {
  public static class Start extends AtomicCommand implements NonDistributiveTurtleCommand {
    public Start () {}
    public TurtleCommand applyTo (final Turtle t) {
      if (t instanceof TriangleEmitter) ((TriangleEmitter) t).start ();
      return this;
    }
  }

  public static class Emit extends AtomicCommand implements NonDistributiveTurtleCommand {
    public Emit () {}
    public TurtleCommand applyTo (final Turtle t) {
      if (t instanceof TriangleEmitter) ((TriangleEmitter) t).emit ();
      return this;
    }
  }

  public T start ();
  public T emit ();
}
