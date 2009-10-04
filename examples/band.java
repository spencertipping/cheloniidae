import cheloniidae.*;
import cheloniidae.frames.*;

public class band extends SingleTurtleScene<PathwiseTriangleConnector<StandardRotationalTurtle>> {
  public static void main (String[] args) {new band ();}

  public PathwiseTriangleConnector<StandardRotationalTurtle> createTurtle () {
    return new PathwiseTriangleConnector<StandardRotationalTurtle> ();
  }

  public TurtleCommand commands () {
    turtle.add (new StandardRotationalTurtle ().position (new Vector (-50, 0, 0)))
          .add (new StandardRotationalTurtle ().position (new Vector (50, 0, 0)).bank (180));

    return repeat (120, move (5), turn (90), jump (50), pitch (3), jump (-50), turn (-90), turtle.emitter ());
  }
}
