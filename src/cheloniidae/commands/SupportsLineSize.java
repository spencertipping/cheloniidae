package cheloniidae.commands;
import cheloniidae.Turtle;
public interface SupportsLineSize<T extends Turtle> {
  public double lineSize ();
  public T      lineSize (double size);
}
