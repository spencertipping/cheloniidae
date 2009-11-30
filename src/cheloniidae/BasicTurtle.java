package cheloniidae;

import cheloniidae.commands.SupportsWindow;

import java.util.TreeSet;
import java.util.Set;

public abstract class BasicTurtle<T extends BasicTurtle> extends Replicable<T> implements Turtle<T>, SupportsWindow {
  public final Set<Attribute> attributes = new TreeSet<Attribute> ();

  protected TurtleWindow window = null;

  public Set<Attribute> attributes ()                           {return attributes;}
  public T              attribute  (final Attribute _attribute) {attributes.add (_attribute); return (T) this;}
  public TurtleWindow   window     ()                           {return window;}
  public T              window     (final TurtleWindow _window) {window = _window; return (T) this;}

  public T run (final TurtleCommand c) {
    c.applyTo (this);
    return (T) this;
  }

  public TurtleCommand map (final Transformation<TurtleCommand> t) {
    final TurtleState serialized = this.serialize ();
    if (serialized instanceof TurtleCommand) return ((TurtleCommand) serialized).map (t);
    else                                     return null;
  }
}
