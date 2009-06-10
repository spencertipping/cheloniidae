package cheloniidae;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class TurtleGroup<T extends Turtle> implements Turtle<TurtleGroup<T>> {
  public static class State implements TurtleState {
    public final Map<Turtle, TurtleState> states = new HashMap<Turtle, TurtleState> ();
    public State (TurtleGroup<T> group) {
      for (Turtle t : group.turtles ()) states.put (t, t.serialize ());
    }
  }

  public TurtleGroup (T ...         _turtles) {for (T t : _turtles) turtles.add (t);}
  public TurtleGroup (T[]           _turtles) {for (T t : _turtles) turtles.add (t);}
  public TurtleGroup (Collection<T> _turtles) {turtles.addAll (_turtles);}

  protected List<T> turtles = new ArrayList<T> ();

  public List<T>        turtles ()                 {return turtles;}
  public TurtleGroup<T> turtles (List<T> _turtles) {turtles = _turtles; return this;}

  public SortedSet<RenderAction> actions (Viewport v) {
    final SortedSet<RenderAction> result = new TreeSet<RenderAction> ();
    for (T t : turtles) result.addAll (t.actions (v));
    return result;
  }

  public State          serialize   () {return new State (this);}
  public TurtleGroup<T> deserialize (TurtleState state) {
    if (state instanceof State) {
      Map<Turtle, TurtleState> stateMap = ((State) state).states;
      for (T t : turtles) t.deserialize (stateMap.get (t));
    }
    return this;
  }

  public TurtleGroup<T> run (TurtleCommand c) {
    for (Turtle t : turtles) t.run (c);
    return this;
  }
}
