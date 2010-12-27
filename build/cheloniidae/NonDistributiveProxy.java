package cheloniidae;

public class NonDistributiveProxy implements NonDistributiveTurtleCommand {
  public final TurtleCommand wrapped;
  public NonDistributiveProxy (final TurtleCommand _wrapped) {wrapped = _wrapped;}

  // This needs to call applyTo instead of t.run because otherwise the turtle will see
  // the (presumably distributive) wrapped command and distribute it normally.
  public TurtleCommand applyTo (final Turtle t) {wrapped.applyTo (t); return this;}
  public TurtleCommand map (final Transformation<TurtleCommand> t) {
    final TurtleCommand newCommand = t.transform (this);
    if (newCommand == this) {
      final TurtleCommand transformedWrapped = wrapped.map (t);
      return transformedWrapped instanceof NonDistributiveTurtleCommand ? transformedWrapped :
                                                                          new NonDistributiveProxy (transformedWrapped);
    } else return newCommand;
  }
}
