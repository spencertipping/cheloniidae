package cheloniidae;
public abstract class ImmutableTurtleState implements TurtleState, TurtleCommand {
  public TurtleCommand map (final Transformation<TurtleCommand> t) {return this;}
}
