package cheloniidae.predicates;

import cheloniidae.Predicate;
import cheloniidae.Transformation;

public abstract class AtomicPredicate<T> implements Predicate<T> {
  public Predicate<T> map (Transformation<Predicate<T>> t) {return this;}
}
