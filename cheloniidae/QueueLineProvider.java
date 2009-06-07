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
    int    ia    = a + 1;
    int    ib    = b;
    int    pivot = (a + b) >> 1;
    double d     = super.get (pivot).cachedDistance;

    swap (a, pivot);
    while (ia < ib) {while (ia < b && super.get (ia).cachedDistance > d) ++ia;
                     while (ib > a && super.get (ib).cachedDistance < d) --ib;
                     if (ia < ib) swap (ia, ib);}
    swap (a, ib);

    if (! cancelSort && a < ib - 2) quicksort (a, ib - 1);
    if (! cancelSort && b > ib)     quicksort (ib + 1, b);
  }

  public final void sort (Vector center) {
    cancelSort = false;
    for (int i = 0; i < super.size (); ++i) super.get (i).cachedDistance = super.get (i).midpoint ().distanceFrom (center);
    quicksort (0, super.size () - 1);
  }

  public final void cancelSort () {cancelSort = true;}
}
