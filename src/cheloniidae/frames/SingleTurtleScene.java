package cheloniidae.frames;

import cheloniidae.*;

public abstract class SingleTurtleScene extends CoreCommands {
  protected final TurtleWindow window;
  protected final Turtle       turtle;

  protected final TurtleStack  stack = new TurtleStack ();

  public abstract TurtleCommand commands ();

  public SingleTurtleScene () {
    window = createWindow ();
    turtle = createTurtle ();
    initialize ();
    run ();
  }

  public Turtle       createTurtle () {return new StandardRotationalTurtle ();}
  public TurtleWindow createWindow () {return new TurtleWindow ();}

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
