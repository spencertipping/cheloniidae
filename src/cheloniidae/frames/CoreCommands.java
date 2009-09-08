package cheloniidae.frames;

import cheloniidae.*;
import cheloniidae.commands.*;
import cheloniidae.replicators.*;
import cheloniidae.transformations.*;

import java.awt.Color;

import java.util.Random;

public abstract class CoreCommands {
  protected Random rng = new Random ();

  public static Move move (double distance) {return new Move (distance);}
  public static Jump jump (double distance) {return new Jump (distance);}

  public static Turn  turn  (double angle) {return new Turn (angle);}
  public static Bank  bank  (double angle) {return new Bank (angle);}
  public static Pitch pitch (double angle) {return new Pitch (angle);}

  public static LineSize  size    (double size)     {return new LineSize (size);}
  public static LineColor color   (Color color)     {return new LineColor (color);}
  public static Visible   visible (boolean visible) {return new Visible (visible);}

  public static Position            position            (Vector position)            {return new Position (position);}
  public static Direction           direction           (Vector direction)           {return new Direction (direction);}
  public static DirectionComplement directionComplement (Vector directionComplement) {return new DirectionComplement (directionComplement);}

  public static NullCommand pass  ()            {return new NullCommand ();}
  public static Debug       debug (String text) {return new Debug (text);}

  public static Repeat          repeat   (int repetitions, TurtleCommand ... commands) {return new Repeat (repetitions, commands);}
  public static CommandSequence sequence (TurtleCommand ... commands)                  {return new CommandSequence (commands);}

  public static InductiveReplicator<StandardRotationalTurtle> inductiveReplicator (int copies, TurtleCommand inductiveStep,
                                                                                   TurtleCommand ... replicatedActions) {
    return new InductiveReplicator<StandardRotationalTurtle> (copies, inductiveStep, replicatedActions);
  }

  public static InductiveReplicator<StandardRotationalTurtle> copy (TurtleCommand ... copiedActions) {
    return inductiveReplicator (1, pass (), sequence (copiedActions));
  }

  public static RecursiveExpansion recursiveBlock (String name, TurtleCommand ... body) {
    return new RecursiveExpansion (name, sequence (body));
  }

  public static RecursiveExpansion.Marker recurse (String name, int remainingExpansions,
                                                   Transformation<TurtleCommand> inductiveTransformation, TurtleCommand ... baseCommands) {
    return new RecursiveExpansion.Marker (name, remainingExpansions, inductiveTransformation, sequence (baseCommands));
  }

  public static     Scale      scale    (double factor)                         {return new Scale (factor);}
  public static     Scale      scale    (double factor, boolean scaleLineSize)  {return new Scale (factor, scaleLineSize);}
  public static     Identity   identity ()                                      {return new Identity ();}
  public static <T> Compose<T> compose  (Transformation<T> ... transformations) {return new Compose<T> (transformations);}

  public double random ()             {return rng.nextDouble ();}
  public double random (double scale) {return rng.nextDouble () * scale;}
}
