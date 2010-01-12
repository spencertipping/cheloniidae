package cheloniidae;

public class NonDistributiveProxy implements NonDistributiveTurtleCommand {
  public final TurtleCommand wrapped;
  public NonDistributiveProxy (final TurtleCommand _wrapped) {wrapped = _wrapped;}

  public TurtleCommand applyTo (final Turtle t) {wrapped.applyTo (t); return this;}
  public TurtleCommand map (final Transformation<TurtleCommand> t) {
    final TurtleCommand newCommand = t.transform (this);
    if (newCommand == this) return new NonDistributiveProxy (wrapped.map (t));
    else                    return newCommand;
  }
}
