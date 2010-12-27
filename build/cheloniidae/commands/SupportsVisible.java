package cheloniidae.commands;
import cheloniidae.Turtle;
public interface SupportsVisible<T extends Turtle> {
  public boolean visible ();
  public T       visible (boolean _visible);
}
