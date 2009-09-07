package cheloniidae.transformations;

import cheloniidae.*;

public class Compose<T> implements Transformation<T> {
  public final Transformation<T>[] transformations;
  public Compose (Transformation<T> ... _transformations) {transformations = _transformations;}

  public T transform (T c) {
    T immediate = c;
    for (int i = transformations.length - 1; i >= 0; --i) immediate = transformations[i].transform (immediate);
    return immediate;
  }
}
