package cheloniidae.commands;

import cheloniidae.Turtle;

public interface SupportsVisible<T extends Turtle> {
  public T visible (boolean _visible);
}
