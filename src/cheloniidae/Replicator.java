package cheloniidae;

public interface Replicator<T extends Turtle> {
  public TurtleGroup<T> replicate (T turtle);
}
