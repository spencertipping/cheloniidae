package cheloniidae;
import cheloniidae.commands.*;
public abstract class Replicable<T extends Turtle> implements Turtle<T>, TurtleCommand {
  public abstract T create ();

  public T clone () {
    final T result = this.create ();
    this.applyTo (result);
    return result;
  }
}
