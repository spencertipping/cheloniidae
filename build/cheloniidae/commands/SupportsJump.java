package cheloniidae.commands;
import cheloniidae.Turtle;
public interface SupportsJump<T extends Turtle> {
  public T jump (double distance);
}
