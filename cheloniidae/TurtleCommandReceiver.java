package cheloniidae;

public interface TurtleCommandReceiver<T implements TurtleCommandReceiver> {
  public T run (TurtleCommand c);
}
