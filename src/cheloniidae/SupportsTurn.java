package cheloniidae;

public interface SupportsTurn<T implements Turtle> {
  public T turn (double angle);
}
