package cheloniidae.predicates;
import cheloniidae.Predicate;
public class True<T> extends AtomicPredicate<T> {
  public boolean matches (final T value) {return true;}
}
