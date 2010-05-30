package cheloniidae.commands;
import cheloniidae.Turtle;
public interface SupportsTurn<T extends Turtle> {
  public T turn (double angle);
}
