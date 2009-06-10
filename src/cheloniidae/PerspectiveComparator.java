package cheloniidae;

import java.util.Comparator;

public final class PerspectiveComparator implements Comparator<HasPerspectiveProjection> {
  public final Viewport v;

  public PerspectiveComparator (Viewport _v) {v = _v;}

  public final int compare (HasPerspectiveProjection p1, HasPerspectiveProjection p2) {
    return Double.compare (p2.depth (v), p1.depth (v));
  }
}
