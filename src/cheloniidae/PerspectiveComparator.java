package cheloniidae;

import java.util.Comparator;

public final class PerspectiveComparator implements Comparator<HasPerspectiveProjection> {
  public final Vector pov;

  public PerspectiveComparator (Vector _pov) {pov = _pov;}

  public final int compare (HasPerspectiveProjection p1, HasPerspectiveProjection p2) {
    return Double.compare (p1.depth (pov), p2.depth (pov));
  }
}
