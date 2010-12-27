package cheloniidae;
import java.io.Serializable;
public interface TurtleCommand extends Transformable<TurtleCommand>, Serializable {
  public TurtleCommand applyTo (Turtle t);
}
