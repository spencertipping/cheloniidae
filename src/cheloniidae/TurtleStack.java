package cheloniidae;

import java.util.Map;
import java.util.HashMap;
import java.util.Stack;

public class TurtleStack {
  protected final Map<Turtle, Stack<TurtleState>> states = new HashMap<Turtle, Stack<TurtleState>> ();

  public TurtleCommand push () {
    return new NonDistributiveTurtleCommand () {
      public TurtleCommand applyTo (Turtle t) {
        if (! states.containsKey (t)) states.put (t, new Stack<TurtleState> ());
        states.get (t).push (t.serialize ());
        return this;
      }
    };
  }

  public TurtleCommand pop () {
    return new NonDistributiveTurtleCommand () {
      public TurtleCommand applyTo (Turtle t) {
        t.deserialize (states.get (t).pop ());
        if (states.get (t).empty ()) states.remove (t);
        return this;
      }
    };
  }
}
