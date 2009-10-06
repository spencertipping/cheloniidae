package cheloniidae.predicates;

import cheloniidae.Attribute;
import cheloniidae.Predicate;
import cheloniidae.Turtle;

public class TurtleAttribute<T extends Turtle> extends AtomicPredicate<T> {
  public final Predicate<Attribute> predicate;
  public TurtleAttribute (final Predicate<Attribute> _predicate) {predicate = _predicate;}
  public boolean matches (final T value) {
    for (final Attribute a : value.attributes ()) if (predicate.matches (a)) return true;
    return false;
  }
}
