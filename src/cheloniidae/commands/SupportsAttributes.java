package cheloniidae.commands;

import cheloniidae.Attribute;
import cheloniidae.Turtle;

import java.util.Set;

public interface SupportsAttributes<T extends Turtle> {
  public Set<Attribute> attributes ();
  public T              attribute  (Attribute a);
}
