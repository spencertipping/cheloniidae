package cheloniidae;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PathwiseTriangleConnector extends Renderable {
  public final List<EuclideanTurtle>   turtles   = new ArrayList<EuclideanTurtle> ();
  public final List<Vector>            points    = new ArrayList<Vector> ();
  public final List<CartesianTriangle> triangles = new LinkedList<CartesianTriangle> ();

  public PathwiseTriangleConnector (final EuclideanTurtle ... _turtles) {
    turtles.addAll (_turtles);
    start ();
  }

  public Set<RenderableAction> actions (final Viewport v) {
    final Set<RenderableAction> result = new TreeSet<RenderableAction> (new PerspectiveComparator (v));
    for (final CartesianTriangle t : triangles) if (v.shouldCancel ()) break;
                                                else                   result.add (t);
    return result;
  }

  public PathwiseTriangleConnector add (final EuclideanTurtle t) {
    turtles.add (t);
    points.add (t.position ().clone ());
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

      public TurtleCommand map (Transformation<TurtleCommand> t) {return this;}
    };
  }

  public TurtleCommand emitter () {
    return new NonDistributiveTurtleCommand () {
      public TurtleCommand applyTo (final Turtle t) {
        emit ();
        return this;
      }

      public TurtleCommand map (Transformation<TurtleCommand> t) {return this;}
    };
  }

  public TurtleCommand adder () {
    return new TurtleCommand () {
      public TurtleCommand applyTo (final Turtle t) {
        if (t instanceof EuclideanTurtle) add ((EuclideanTurtle) t);
        return this;
      }

      public TurtleCommand map (Transformation<TurtleCommand> t) {return this;}
    };
  }
}
