package cheloniidae.frames;

import cheloniidae.*;

public abstract class SingleTurtleScene extends CoreCommands {
  protected final TurtleWindow             window = new TurtleWindow ();
  protected final StandardRotationalTurtle turtle = new StandardRotationalTurtle ();
  protected final TurtleStack              stack  = new TurtleStack ();

  public abstract TurtleCommand commands ();

  public SingleTurtleScene () {
    initialize ();
    run ();
  }

  public SingleTurtleScene initialize () {
    window.add (turtle).setVisible (true);
    return this;
  }

  public SingleTurtleScene run () {
    turtle.run (this.commands ());
    window.pause (0);
    return this;
  }
} 
