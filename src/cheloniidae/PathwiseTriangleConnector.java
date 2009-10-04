package cheloniidae;

import cheloniidae.transformations.Parallelize;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class PathwiseTriangleConnector<T extends EuclideanTurtle> extends TurtleGroup<T> {
  public final List<Vector>            points    = new ArrayList<Vector> ();
  public final List<CartesianTriangle> triangles = new LinkedList<CartesianTriangle> ();

  public PathwiseTriangleConnector (final T ...         _turtles) {super (_turtles); start ();}
  public PathwiseTriangleConnector (final Collection<T> _turtles) {super (_turtles); start ();}

  public SortedSet<RenderAction> actions (final Viewport v) {
    final SortedSet<RenderAction> result = new TreeSet<RenderAction> (new PerspectiveComparator (v));
    for (final CartesianTriangle t : triangles) if (v.shouldCancel ()) break;
                                                else                   result.add (t);
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

        triangles.add (new CartesianTriangle (tv1, sv1, sv2, t1.color ()));
        triangles.add (new CartesianTriangle (tv1, tv2, sv2, t2.color ()));
      }

    return start ();
  }

  public TurtleCommand starter () {
    return new NonDistributiveTurtleCommand () {
      public TurtleCommand applyTo (final Turtle t) {
        start ();
        return this;
      }

      public TurtleCommand map (final Transformation<TurtleCommand> t) {return this;}
    };
  }

  public TurtleCommand emitter () {
    return new NonDistributiveTurtleCommand () {
      public TurtleCommand applyTo (final Turtle t) {
        emit ();
        return this;
      }

      public TurtleCommand map (final Transformation<TurtleCommand> t) {return this;}
    };
  }

  public PathwiseTriangleConnector<T> run (final TurtleCommand c) {
    super.run (new Parallelize ().transform (c));
    return this;
  }
}
