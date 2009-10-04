package cheloniidae;

public class Negation<T> implements Predicate<T> {
  public final Predicate<T> predicate;
  public Negation (final Predicate<T> _predicate) {predicate = _predicate;}
  public boolean matches (final T value) {return ! predicate.matches (value);}
}
