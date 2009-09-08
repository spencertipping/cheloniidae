package cheloniidae;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class TurtleGroup<T extends Turtle> extends BasicTurtle<TurtleGroup<T>> implements Turtle<TurtleGroup<T>>, TurtleCommand {
  public static class State extends ImmutableTurtleState implements NonDistributiveTurtleCommand {
    // Turtle states are stored positionally instead of by some form of map because of replication. We need to be able to produce a turtle state object that can
    // apply itself to a cloned group, and the cloned group won't share object identity with the original.
    public final List<TurtleState> states = new ArrayList<TurtleState> ();
    public State (TurtleGroup group) {
      for (Turtle t : (List<Turtle>) group.turtles ()) states.add (t.serialize ());
    }

    public State applyTo (Turtle t) {
      t.deserialize (this);
      return this;
    }
  }

  public TurtleGroup (T ...         _turtles) {for (T t : _turtles) turtles.add (t);}
  public TurtleGroup (Collection<T> _turtles) {turtles.addAll (_turtles);}

  protected List<T> turtles = new ArrayList<T> ();

  public TurtleGroup<T> create () {
    TurtleGroup<T> result = new TurtleGroup<T> ();
    for (Turtle t : turtles ()) result.turtles ().add ((T) t.clone ());
    return result;
  }

  public List<T>        turtles ()                     {return turtles;}
  public TurtleGroup<T> turtles (List<T> _turtles)     {turtles = _turtles; return this;}
  public TurtleGroup<T> window  (TurtleWindow _window) {super.window (_window);
                                                        for (T t : turtles) t.window (window);
                                                        return this;}

  public TurtleGroup<T> add (T turtle) {
    turtles.add (turtle);
    turtle.window (window ());
    return this;
  }

  public SortedSet<RenderAction> actions (Viewport v) {
    final SortedSet<RenderAction> result = new TreeSet<RenderAction> (new PerspectiveComparator (v));
    for (T t : turtles) if (! v.shouldCancel ()) result.addAll (t.actions (v));
    return result;
  }

  public State          serialize   () {return new State (this);}
  public TurtleGroup<T> deserialize (TurtleState state) {
    if (state instanceof State) {
      List<TurtleState> stateList = ((State) state).states;
      for (int i = 0; i < stateList.size (); ++i) turtles ().get (i).deserialize (stateList.get (i));
    }
    return this;
  }

  public TurtleGroup<T> applyTo (Turtle t) {
    serialize ().applyTo (t);
    return this;
  }

  public TurtleGroup<T> run (TurtleCommand c) {
    if (c instanceof NonDistributiveTurtleCommand) c.applyTo (this);
    else for (T t : turtles) t.run (c);
    return this;
  }
}
