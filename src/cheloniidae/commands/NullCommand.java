package cheloniidae.commands;

import cheloniidae.Turtle;
import cheloniidae.TurtleCommand;

public class NullCommand implements TurtleCommand {
  public NullCommand applyTo (Turtle t) {return this;}
}
