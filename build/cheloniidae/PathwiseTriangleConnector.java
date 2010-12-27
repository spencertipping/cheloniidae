package cheloniidae;

import cheloniidae.transformations.Parallelize;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class PathwiseTriangleConnector<T extends EuclideanTurtle>
     extends TurtleGroup<T>
  implements TriangleEmitter<PathwiseTriangleConnector<T>> {

  public final List<Vector>            points    = new ArrayList<Vector> ();
  public final List<CartesianTriangle> triangles = new LinkedList<CartesianTriangle> ();

  public PathwiseTriangleConnector (final T ...         _turtles) {super (_turtles); start ();}
  public PathwiseTriangleConnector (final Collection<T> _turtles) {super (_turtles); start ();}

  public SortedSet<RenderAction> actions (final Viewport v) {
    final SortedSet<RenderAction> result = super.actions (v);
    for (final CartesianTriangle t : triangles) if (v.shouldCancel ()) break;
                                                else                   result.add (t);
    return result;
  }

  public PathwiseTriangleConnector<T> create () {
    final PathwiseTriangleConnector<T> result = new PathwiseTriangleConnector<T> ();
    for (final T t : turtles) result.add ((T) t.clone ());
    result.start ();
    return result;
  }

  public PathwiseTriangleConnector<T> add (final T turtle) {
    super.add (turtle);
    points.add (turtle.position ().clone ());
    return this;
  }

  public PathwiseTriangleConnector start () {
    // Keep track of all of the locations of all of the turtles.
    points.clear ();
    for (final EuclideanTurtle t : turtles) points.add (t.position ().clone ());
    return this;
  }

  public PathwiseTriangleConnector emit () {
    // Emit triangles connecting all of the turtles. For n turtles, there will be O(n^2) triangles.
    for (int i = 0; i < turtles.size () - 1; ++i)
      for (int j = i + 1; j < turtles.size (); ++j) {
        final EuclideanTurtle t1  = turtles.get (i);
        final EuclideanTurtle t2  = turtles.get (j);
        final Vector          tv1 = t1.position ();
        final Vector          tv2 = t2.position ();
        final Vector          sv1 = points.get (i);
        final Vector          sv2 = points.get (j);

        // If the triangle's area is below a certain amount, then we don't add the triangle.
        // This is to prevent degenerate triangles from slowing the scene render operations,
        // which might occur if the user moved one turtle but not the other.
        if (tv1.clone ().subtract (sv1).cross (sv2.clone ().subtract (sv1)).lengthSquared () > 1.0e-10)
          triangles.add (new CartesianTriangle (tv1, sv1, sv2, t1.color ()));

        if (tv1.clone ().subtract (tv2).cross (sv2.clone ().subtract (tv2)).lengthSquared () > 1.0e-10)
          triangles.add (new CartesianTriangle (tv1, tv2, sv2, t2.color ()));
      }

    return start ();
  }

  public PathwiseTriangleConnector<T> run (final TurtleCommand c) {
    super.run (c.map (new Parallelize ()));
    return this;
  }
}
