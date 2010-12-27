package cheloniidae.commands;
import cheloniidae.Turtle;
public interface SupportsMove<T extends Turtle> {
  public T move (double distance);
}
