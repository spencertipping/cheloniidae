package cheloniidae.commands;
import cheloniidae.Turtle;
public interface SupportsPitch<T extends Turtle> {
  public T pitch (double angle);
}
