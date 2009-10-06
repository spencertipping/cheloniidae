package cheloniidae.commands;

import java.awt.Color;
import java.util.Set;
import java.util.HashSet;

import cheloniidae.*;

public class Triangle extends AtomicCommand {
  public final SupportsPosition p1;
  public final SupportsPosition p2;

  public Triangle (final SupportsPosition _p1, final SupportsPosition _p2) {p1 = _p1; p2 = _p2;}

  public TurtleCommand applyTo (final Turtle t) {
    if (t instanceof SupportsPosition && t instanceof SupportsLineColor) {
      final SupportsPosition  p3      = (SupportsPosition) t;
      final Color             color   = ((SupportsLineColor) t).lineColor ();
      final Set<RenderAction> actions = new HashSet<RenderAction> ();

      actions.add (new CartesianTriangle (p1.position (), p2.position (), p3.position (), color));
      t.window ().add (new PreloadedTurtle (actions));
    }

    return this;
  }
}
