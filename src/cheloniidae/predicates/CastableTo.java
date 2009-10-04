package cheloniidae.predicates;

import cheloniidae.Predicate;
import cheloniidae.Transformation;

public class CastableTo<T, U extends T> extends AtomicPredicate<T> {
  public final Class<U>     type;
  public final Predicate<U> predicate;
  public CastableTo (final Class<U> _type, final Predicate<U> _predicate)
    {type = _type; predicate = _predicate;}

  public boolean matches (final T value) {
    return type.isAssignableFrom (value.getClass ()) && predicate.matches (type.cast (value));
  }
}
