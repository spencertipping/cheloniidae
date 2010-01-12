package cheloniidae;

import java.util.Comparator;

public final class PerspectiveComparator implements Comparator<HasPerspectiveProjection> {
  public final Viewport v;
  public PerspectiveComparator (final Viewport _v) {v = _v;}

  public final int compare (final HasPerspectiveProjection p1, final HasPerspectiveProjection p2) {
    // A couple of notes about this. First, notice that we're comparing in reverse. This is intentional.
    // We want to sort such that the objects farthest away from the point of view are at the beginning,
    // since they should be rendered first. Second, notice that we never return equality. This is because
    // a TreeSet is a proper set, not just a bag of elements. Items that compare equal belong to the same
    // equivalence class, so they would be considered duplicates. Thus we make sure that the comparison
    // never returns equality. (This was the cause of a weird bug.)

    return p2.depth (v) < p1.depth (v) ? -1 : 1;
  }
}
