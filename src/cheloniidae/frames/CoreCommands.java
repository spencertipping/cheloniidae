package cheloniidae.frames;

import cheloniidae.*;
import cheloniidae.commands.*;

import java.awt.Color;

public class CoreCommands {
  public static TurtleCommand move (double distance) {return new Move (distance);}
  public static TurtleCommand jump (double distance) {return new Jump (distance);}

  public static TurtleCommand turn  (double angle) {return new Turn (angle);}
  public static TurtleCommand bank  (double angle) {return new Bank (angle);}
  public static TurtleCommand pitch (double angle) {return new Pitch (angle);}

  public static TurtleCommand size  (double size) {return new LineSize (size);}
  public static TurtleCommand color (Color color) {return new LineColor (color);}

  public static TurtleCommand position            (Vector position)            {return new Position (position);}
  public static TurtleCommand direction           (Vector direction)           {return new Direction (direction);}
  public static TurtleCommand directionComplement (Vector directionComplement) {return new DirectionComplement (directionComplement);}

  public static TurtleCommand pass () {return new NullCommand ();}

  public static TurtleCommand repeat   (int repetitions, TurtleCommand ... commands) {return new Repeat (repetitions, commands);}
  public static TurtleCommand sequence (TurtleCommand ... commands)                  {return new CommandSequence (commands);}
}
