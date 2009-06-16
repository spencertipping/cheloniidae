package cheloniidae.frames;

import cheloniidae.*;

public abstract class SingleTurtleScene extends CoreCommands {
  protected final TurtleWindow                     window = new TurtleWindow ();
  protected final DefaultRotationalCartesianTurtle turtle = new DefaultRotationalCartesianTurtle ();
  protected final TurtleStack                      stack  = new TurtleStack ();

  public abstract TurtleCommand run ();

  public SingleTurtleScene () {
    window.add (turtle).setVisible (true);
    turtle.run (run ());
    window.pause (0);
  }
} 
