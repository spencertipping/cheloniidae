package cheloniidae.predicates;

import cheloniidae.Predicate;

public class CastableTo<T> implements Predicate<Object> {
  public final Class<T>     type;
  public final Predicate<T> predicate;
  public CastableTo (final Class<T> _type, final Predicate<T> _predicate)
    {type = _type; predicate = _predicate;}

  public boolean matches (final Object value) {
    return type.isAssignableFrom (value.getClass ()) && predicate.matches (type.cast (value));
  }

  public Predicate<Object> map (final Transformation<Predicate<Object>> t) {
    final Predicate<Object> newPredicate = t.transform (this);
    if (newPredicate == this) return new CastableTo<T> (type, predicate.map (t));
    else                      return newPredicate;
  }
}
