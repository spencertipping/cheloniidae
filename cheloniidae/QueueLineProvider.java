// Cheloniidae Turtle Graphics
// Created by Spencer Tipping, licensed under the MIT source code license

package cheloniidae;

import java.util.ArrayList;

public class QueueLineProvider extends ArrayList<Line> implements LineProvider {
  protected boolean cancelSort = false;

  private final void swap (int a, int b) {
    Line temp = super.get (a);
    super.set (a, super.get (b));
    super.set (b, temp);
  }

  private final void quicksort (int a, int b) {
    if (a < b) {
      int    ia    = a + 1;
      int    ib    = b + 1;
      int    pivot = (a + b) >> 1;
      double d     = super.get (pivot).cachedDistance;

      swap (a, pivot);
      while (ia < ib) {while (ia < b && super.get (ia).cachedDistance > d) ++ia;
                       while           (super.get (--ib).cachedDistance < d);
                       if (ia < ib) swap (ia, ib);}
      swap (a, ib);

      quicksort (a, ib - 1);
      quicksort (ib + 1, b);
    }
  }

  public void sort (Vector center) {
    cancelSort = false;
    for (int i = 0; i < super.size (); ++i) super.get (i).cachedDistance = super.get (i).midpoint ().distanceFrom (center);
    quicksort (0, super.size () - 1);
  }

  public void cancelSort () {cancelSort = true;}
}
