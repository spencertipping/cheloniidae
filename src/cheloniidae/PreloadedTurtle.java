package cheloniidae;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class PreloadedTurtle<T extends PreloadedTurtle> extends BasicTurtle<T> {
  public final Set<RenderAction> actions;
  public PreloadedTurtle (final Set<RenderAction> _actions) {actions = _actions;}

  public SortedSet<RenderAction> actions (final Viewport v) {
    final SortedSet<RenderAction> result = new TreeSet<RenderAction> (new PerspectiveComparator (v));
    for (final RenderAction a : actions) if (v.shouldCancel ()) break;
                                         else                   result.add (a);
    return result;
  }

  public TurtleState serialize   ()                    {return null;}
  public T           deserialize (final TurtleState t) {return (T) this;}

  public T             create  ()               {return (T) this;}
  public T             clone   ()               {return (T) this;}
  public TurtleCommand applyTo (final Turtle t) {return this;}
}
