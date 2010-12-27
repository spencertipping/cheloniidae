package cheloniidae;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Disjunction<T> implements Predicate<T> {
  public final List<Predicate<T>> disjuncts = new LinkedList<Predicate<T>> ();
  public Disjunction (final Predicate<T> ... _disjuncts)
    {for (final Predicate<T> p : _disjuncts) disjuncts.add (p);}
  public Disjunction (final Collection<Predicate<T>> _disjuncts) {disjuncts.addAll (_disjuncts);}

  public boolean matches (final T value) {
    for (final Predicate<T> p : disjuncts) if (p.matches (value)) return true;
    return false;
  }

  public Predicate<T> map (final Transformation<Predicate<T>> t) {
    final Predicate<T> newPredicate = t.transform (this);
    if (newPredicate == this) {
      final List<Predicate<T>> newDisjuncts = new LinkedList<Predicate<T>> ();
      for (final Predicate<T> p : disjuncts) newDisjuncts.add (p.map (t));
      return new Disjunction<T> (newDisjuncts);
    } else return newPredicate;
  }
}
