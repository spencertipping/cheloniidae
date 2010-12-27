package cheloniidae.commands;
import cheloniidae.Turtle;
public interface SupportsBank<T extends Turtle> {
  public T bank (double angle);
}
