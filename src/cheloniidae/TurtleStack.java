package cheloniidae;

import java.util.Map;
import java.util.HashMap;
import java.util.Stack;

public class TurtleStack {
  public class Push extends ImmutableTurtleState implements NonDistributiveTurtleCommand {
    public TurtleCommand applyTo (final Turtle t) {
      if (! states.containsKey (t)) states.put (t, new Stack<TurtleState> ());
      states.get (t).push (t.serialize ());
      return this;
    }
  }

  public class Pop extends ImmutableTurtleState implements NonDistributiveTurtleCommand {
    public TurtleCommand applyTo (final Turtle t) {
      t.deserialize (states.get (t).pop ());
      if (states.get (t).empty ()) states.remove (t);
      return this;
    }
  }

  protected final Map<Turtle, Stack<TurtleState>> states = new HashMap<Turtle, Stack<TurtleState>> ();

  public TurtleCommand push () {return new Push ();}
  public TurtleCommand pop  () {return new Pop ();}
}
