import cheloniidae.*;
import cheloniidae.frames.*;

public class pyramid extends SingleTurtleScene<PathwiseTriangleConnector<StandardRotationalTurtle>> {
  public static void main (String[] args) {new pyramid ();}

  public PathwiseTriangleConnector<StandardRotationalTurtle> createTurtle () {
    return new PathwiseTriangleConnector<StandardRotationalTurtle> ();
  }

  public TurtleCommand commands () {
    turtle.add (new StandardRotationalTurtle ().attribute (named ("bob"))).add (new StandardRotationalTurtle ().turn (60).jump (100));

    return when (turtleAttribute (named ("bob").projectivePredicate ()),
                 move (100), turtle.emitter (),
                 turn (30), bank (30), pitch (-90), move (100), turtle.emitter ()
                     
                  );
  }
}
