import cheloniidae.*;
import cheloniidae.frames.*;

import static cheloniidae.frames.CoreCommands.*;
import static java.lang.Math.sin;

public class sphereflake extends SingleTurtleScene {
  public static void main (String[] args) {new sphereflake ();}

  public TurtleCommand commands () {
    final StandardRotationalTurtle guideTurtle        = new StandardRotationalTurtle ().attribute (named ("guide"));
    final Predicate<Turtle>        isGuideTurtle      = turtleAttribute (named ("guide").projectivePredicate ());
    final TurtleCommand            synchronizeTurtles = guideTurtle;

    final int                      majorStep          = 20;
    final int                      minorStep          = 30;
    final double                   scale              = 20;
    final double                   radius             = scale * sin ((90 - 180 / minorStep) * Math.PI / 180.0) /
                                                                sin ((360 / minorStep) * Math.PI / 180.0);

    turtle.add (guideTurtle).add (new StandardRotationalTurtle ());

    final TurtleCommand sphere =
      bandSplitReplicator (turn (180 / majorStep),
                           pitch (-90), jump (radius), pitch (90),
                           turtle.starter (),
                           repeat (majorStep, turn (180 / majorStep),
                                              pitch (180 / minorStep),
                                              repeat (minorStep, move (scale), turtle.emitter (), pitch (360 / minorStep)),
                                              pitch (-180 / minorStep)));

    final TurtleCommand go = jump (radius * 4.0 / 3.0);
    final TurtleCommand recursiveStep = atomic (recurse ("sphereflake", 3, scale (1.0 / 3.0), visible (false)));

    return atomic (recursiveBlock ("sphereflake", sphere,
                                   atomic (inductiveReplicator (6, turn (60),  go, recursiveStep)),
                                   turn (30),
                                   atomic (inductiveReplicator (3, turn (120), pitch (-60), go, recursiveStep)),
                                   turn (-30),
                                   atomic (inductiveReplicator (3, turn (120), pitch (60),  go, recursiveStep))));
  }
}
