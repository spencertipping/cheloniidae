package cheloniidae;

public class PredicatedTransformation<T> implements Transformation<T>, Predicate<T> {
  public final Predicate<T>      predicate;
  public final Transformation<T> transformation;

  public PredicatedTransformation (final Predicate<T> _predicate, final Transformation<T> _transformation)
    {predicate = _predicate; transformation = _transformation;}

  public T transform (final T input) {
    if (predicate.matches (input)) return transformation.transform (input);
    else                           return input;
  }

  public boolean matches (final T value) {
    return predicate.matches (value);
  }
}
