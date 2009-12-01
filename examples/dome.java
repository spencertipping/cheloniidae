import cheloniidae.*;
import cheloniidae.frames.*;

import static cheloniidae.frames.CoreCommands.*;

public class dome extends SingleTurtleScene<PathwiseTriangleConnector<StandardRotationalTurtle>> {
  public static void main (String[] args) {new sphereflake ();}

  public PathwiseTriangleConnector<StandardRotationalTurtle> createTurtle () {
    return new PathwiseTriangleConnector<StandardRotationalTurtle> ();
  }

  public TurtleCommand commands () {
    final StandardRotationalTurtle guideTurtle        = new StandardRotationalTurtle ().attribute (named ("guide"));
    final Predicate<Turtle>        isGuideTurtle      = turtleAttribute (named ("guide").projectivePredicate ());
    final TurtleCommand            synchronizeTurtles = guideTurtle;

    turtle.add (guideTurtle).add (new StandardRotationalTurtle ());

    final double        scale  = 4;
    final TurtleCommand sphere =
      sequence (synchronizeTurtles,
                when (isGuideTurtle, turn (6)),
                repeat (30, turn (6),
                            pitch (3),
                            repeat (60, move (scale), triangleEmit (), pitch (6)),
                            pitch (3)));

    return sphere;
  }
}
