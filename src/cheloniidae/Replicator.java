package cheloniidae;

public abstract class Replicator implements NonDistributiveTurtleCommand {
  public abstract TurtleGroup<Turtle> replicate (Turtle base);
}
