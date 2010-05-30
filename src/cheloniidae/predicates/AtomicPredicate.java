package cheloniidae.predicates;
import cheloniidae.Predicate;
import cheloniidae.Transformation;
public abstract class AtomicPredicate<T> implements Predicate<T> {
  public Predicate<T> map (final Transformation<Predicate<T>> t) {return t.transform (this);}
}
