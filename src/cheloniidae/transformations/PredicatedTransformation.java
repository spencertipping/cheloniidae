package cheloniidae.transformations;
import cheloniidae.*;
public class PredicatedTransformation<T extends Transformable<T>> implements Transformation<T> {
  public final Predicate<T>      predicate;
  public final Transformation<T> transformation;

  public PredicatedTransformation (final Predicate<T> _predicate, final Transformation<T> _transformation)
    {predicate = _predicate; transformation = _transformation;}

  public T transform (final T input) {
    if (predicate.matches (input)) return input.map (transformation);
    else                           return input;
  }
}
