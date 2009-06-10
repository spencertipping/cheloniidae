package cheloniidae.commands;

import cheloniidae.Turtle;

import java.awt.Color;

public interface SupportsBodyColor<T implements Turtle> {
  public T bodyColor (Color c);
}
