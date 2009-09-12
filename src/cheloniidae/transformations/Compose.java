package cheloniidae.transformations;

import cheloniidae.*;

public class Compose<T> implements Transformation<T> {
  // Note! This class works backwards from the mathematical compose. That is, it evaluates from left to right, passing the input rightward.
  public final Transformation<T>[] transformations;
  public Compose (final Transformation<T> ... _transformations) {transformations = _transformations;}

  public T transform (final T c) {
    T immediate = c;
    for (final Transformation<T> t : transformations) immediate = t.transform (immediate);
    return immediate;
  }
}
