package cheloniidae;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Conjunction<T> implements Predicate<T> {
  public final List<Predicate<T>> conjuncts = new LinkedList<Predicate<T>> ();
  public Conjunction (final Predicate<T> ... _conjuncts)
    {for (final Predicate<T> p : _conjuncts) conjuncts.add (p);}
  public Conjunction (final Collection<Predicate<T>> _conjuncts) {conjuncts.addAll (_conjuncts);}

  public boolean matches (final T value) {
    for (final Predicate<T> p : conjuncts) if (! p.matches (value)) return false;
    return true;
  }

  public Predicate<T> map (final Transformation<Predicate<T>> t) {
    final Predicate<T> newPredicate = t.transform (this);
    if (newPredicate == this) {
      final List<Predicate<T>> newConjuncts = new LinkedList<Predicate<T>> ();
      for (final Predicate<T> p : conjuncts) newConjuncts.add (p.map (t));
      return new Conjunction<T> (newConjuncts);
    } else return newPredicate;
  }
}
