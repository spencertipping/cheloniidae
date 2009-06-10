package cheloniidae.commands;

import cheloniidae.Turtle;

import java.awt.Color;

public interface SupportsLineColor<T implements Turtle> {
  public T lineColor (Color c);
}
