package cheloniidae.predicates;
import cheloniidae.*;
public class Negation<T> implements Predicate<T> {
  public final Predicate<T> predicate;
  public Negation (final Predicate<T> _predicate) {predicate = _predicate;}
  public boolean matches (final T value) {return ! predicate.matches (value);}

  public Predicate<T> map (final Transformation<Predicate<T>> t) {
    final Predicate<T> newPredicate = t.transform (this);
    if (newPredicate == this) return new Negation (predicate.map (t));
    else                      return newPredicate;
  }
}
