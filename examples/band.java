import cheloniidae.*;
import cheloniidae.frames.*;

public class band extends SingleTurtleScene<PathwiseTriangleConnector<StandardRotationalTurtle>> {
  public static void main (String[] args) {new band ();}

  public PathwiseTriangleConnector<StandardRotationalTurtle> createTurtle () {
    return new PathwiseTriangleConnector<StandardRotationalTurtle> ();
  }

  public TurtleCommand commands () {
    turtle.add (new StandardRotationalTurtle ()).add (new StandardRotationalTurtle ().position (new Vector (5, 0, 0)));
    return repeat (120, move (5), bank (3), pitch (3), turtle.emitter ());
  }
}
