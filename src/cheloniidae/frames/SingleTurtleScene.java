package cheloniidae.frames;

import cheloniidae.*;

public abstract class SingleTurtleScene<T extends Turtle> {
  protected final TurtleWindow window;
  protected final T            turtle;

  protected final TurtleStack  stack = new TurtleStack ();

  public abstract TurtleCommand commands ();

  public SingleTurtleScene () {
    window = createWindow ();
    turtle = createTurtle ();
    initialize ();
    run ();
  }

  public T            createTurtle () {return (T) new StandardRotationalTurtle ();}
  public TurtleWindow createWindow () {return new TurtleWindow ();}

  public SingleTurtleScene<T> initialize () {
    window.add (turtle).setVisible (true);
    return this;
  }

  public SingleTurtleScene<T> run () {
    turtle.run (this.commands ());
    window.pause (0);
    return this;
  }
} 
