package cheloniidae.transformations;
import cheloniidae.*;
public class Compose<T extends Transformable<T>> implements Transformation<T> {
  public final Transformation<T>[] transformations;
  public Compose (final Transformation<T> ... _transformations) {transformations = _transformations;}

  public T transform (final T c) {
    T immediate = c;
    for (final Transformation<T> t : transformations) immediate = immediate.map (t);
    return immediate;
  }
}
