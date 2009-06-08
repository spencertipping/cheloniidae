// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae;

import java.util.ArrayList;

public class UnionLineProvider extends QueueLineProvider implements LineProvider {
  protected ArrayList<LineProvider> providers = new ArrayList<LineProvider> ();

  public ArrayList<LineProvider> providers ()                                   {return providers;}
  public UnionLineProvider       providers (ArrayList<LineProvider> _providers) {providers = _providers; return this;}

  public UnionLineProvider add (LineProvider p) {providers.add (p); return this;}

  public void sort (Vector center) {
    // This is a naive algorithm for solving the problem, but it would be much more involved to solve it correctly.
    // This ultimately reduces to a mergesort, which would involve allocating extra memory. While memory is allocated
    // here, it is done only once; that is, the array is expanded in one big series of insertions. After that, a large
    // quicksort is done.
    //
    // Each provider must be sorted on its own because the sort serves as the initialization procedure. Once sorted,
    // a provider can meaningfully provide a list of lines.
    //
    // No algorithm efficiency can be gained by circumventing the clear() and refill cycle. The reason is that assuming
    // that we have an ordering on the set of lines (the cachedDistance is a convenient one, though there may be
    // reasons that it isn't feasible), checking for line membership is O(log n). For n lines, we have O(n log n). This
    // is the average runtime of the quicksort and the best-case for any sort; thus, O(n log n) is the best we can
    // possibly do here.

    super.clear ();
    for (LineProvider p : providers) {p.sort (center);
                                      for (int i = 0; i < p.size (); ++i) super.add (p.get (i));}
    super.sort (center);
  }
}
