package cheloniidae.frames;

import cheloniidae.*;
import cheloniidae.commands.*;

import java.awt.Color;

import java.util.Random;

public class CoreCommands {
  public static final Random rng = new Random ();

  public static Move move (double distance)        {return new Move (distance);}
  public static Move move (Proxy<Double> distance) {return new Move (distance);}
  public static Jump jump (double distance)        {return new Jump (distance);}
  public static Jump jump (Proxy<Double> distance) {return new Jump (distance);}

  public static Turn  turn  (double angle)        {return new Turn (angle);}
  public static Turn  turn  (Proxy<Double> angle) {return new Turn (angle);}
  public static Bank  bank  (double angle)        {return new Bank (angle);}
  public static Bank  bank  (Proxy<Double> angle) {return new Bank (angle);}
  public static Pitch pitch (double angle)        {return new Pitch (angle);}
  public static Pitch pitch (Proxy<Double> angle) {return new Pitch (angle);}

  public static LineSize  size  (double size)        {return new LineSize (size);}
  public static LineSize  size  (Proxy<Double> size) {return new LineSize (size);}
  public static LineColor color (Color color)        {return new LineColor (color);}
  public static LineColor color (Proxy<Color> color) {return new LineColor (color);}

  public static Position            position            (Vector position)                   {return new Position (position);}
  public static Position            position            (Proxy<Vector> position)            {return new Position (position);}
  public static Direction           direction           (Vector direction)                  {return new Direction (direction);}
  public static Direction           direction           (Proxy<Vector> direction)           {return new Direction (direction);}
  public static DirectionComplement directionComplement (Vector directionComplement)        {return new DirectionComplement (directionComplement);}
  public static DirectionComplement directionComplement (Proxy<Vector> directionComplement) {return new DirectionComplement (directionComplement);}

  public static NullCommand pass  ()                     {return new NullCommand ();}
  public static Debug       debug (String prefix)        {return new Debug (prefix);}
  public static Debug       debug (Proxy<String> prefix) {return new Debug (prefix);}

  public static Repeat          repeat   (int repetitions, TurtleCommand ... commands) {return new Repeat (repetitions, commands);}
  public static CommandSequence sequence (TurtleCommand ... commands)                  {return new CommandSequence (commands);}

  public static Proxy<Double> random ()                     {return random (1.0);}
  public static Proxy<Double> random (final double ceiling) {
    return new Proxy<Double> () {
      public Double evaluate () {
        return rng.nextDouble () * ceiling;
      }
    };
  }

  public static double randomNumber ()                     {return randomNumber (1.0);}
  public static double randomNumber (final double ceiling) {
    return rng.nextDouble () * ceiling;
  }
}
