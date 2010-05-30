package cheloniidae.commands;

import java.awt.Color;
import java.util.Set;
import java.util.HashSet;

import cheloniidae.*;

public class Triangle implements TurtleCommand {
  public final SupportsPosition p1;
  public final SupportsPosition p2;

  public final TurtleCommand c1;
  public final TurtleCommand c2;

  public Triangle (final SupportsPosition _p1, final SupportsPosition _p2) {
    p1 = _p1; p2 = _p2;
    c1 = c2 = null;
  }

  public Triangle (final TurtleCommand _c1, final TurtleCommand _c2) {
    p1 = p2 = null;
    c1 = _c1; c2 = _c2;
  }

  public TurtleCommand applyTo (final Turtle t) {
    if (t instanceof SupportsPosition && t instanceof SupportsLineColor) {
      final SupportsPosition  p3      = (SupportsPosition) t;
      final Color             color   = ((SupportsLineColor) t).lineColor ();
      final Set<RenderAction> actions = new HashSet<RenderAction> ();

      if (p1 != null) actions.add (new CartesianTriangle (p1.position (), p2.position (), p3.position (), color));
      else {
        // Construct positions by creating temporary clones of the turtle.
        final Turtle clone1 = t.clone ().run (c1);
        final Turtle clone2 = t.clone ().run (c2);

        actions.add (new CartesianTriangle (((SupportsPosition) clone1).position (),
                                            ((SupportsPosition) clone2).position (),
                                            p3.position (), color));
      }

      t.window ().add (new PreloadedTurtle (actions));
    }

    return this;
  }

  public TurtleCommand map (final Transformation<TurtleCommand> t) {
    final TurtleCommand newCommand = t.transform (this);
    if (newCommand == this && c1 != null) return new Triangle (c1.map (t), c2.map (t));
    else                                  return newCommand;
  }
}
