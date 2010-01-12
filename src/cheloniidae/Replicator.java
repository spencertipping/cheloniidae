package cheloniidae;
public interface Replicator extends NonDistributiveTurtleCommand {
  public TurtleGroup<Turtle> replicate (Turtle base);
}
