package cheloniidae;

public abstract class ImmutableTurtleState implements TurtleState, TurtleCommand {
  public TurtleCommand map (Transformation<TurtleCommand> t) {return this;}
}
