package cheloniidae;

import java.util.LinkedList;
import java.util.List;

public class Disjunction<T> implements Predicate<T> {
  public final List<Predicate<T>> disjuncts = new LinkedList<Predicate<T>> ();
  public Disjunction (final Predicate<T> ... _disjuncts)         {for (final Predicate<T> p : _disjuncts) disjuncts.add (p);}
  public Disjunction (final Collection<Predicate<T>> _disjuncts) {disjuncts.addAll (_disjuncts);}

  public boolean matches (final T value) {
    for (final Predicate<T> p : disjuncts) if (p.matches (value)) return true;
    return false;
  }
}
