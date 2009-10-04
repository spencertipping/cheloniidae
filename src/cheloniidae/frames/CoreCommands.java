package cheloniidae.frames;

import cheloniidae.*;
import cheloniidae.commands.*;
import cheloniidae.replicators.*;
import cheloniidae.transformations.*;

import java.awt.Color;

import java.util.Random;

public abstract class CoreCommands {
  protected final Random rng = new Random ();

  public static Move move (final double distance) {return new Move (distance);}
  public static Jump jump (final double distance) {return new Jump (distance);}

  public static Turn  turn  (final double angle) {return new Turn (angle);}
  public static Bank  bank  (final double angle) {return new Bank (angle);}
  public static Pitch pitch (final double angle) {return new Pitch (angle);}

  public static LineSize  size    (final double size)     {return new LineSize (size);}
  public static LineColor color   (final Color color)     {return new LineColor (color);}
  public static Visible   visible (final boolean visible) {return new Visible (visible);}

  public static Position            position            (final Vector position)            {return new Position (position);}
  public static Direction           direction           (final Vector direction)           {return new Direction (direction);}
  public static DirectionComplement directionComplement (final Vector directionComplement) {return new DirectionComplement (directionComplement);}

  public static NullCommand pass  ()                  {return new NullCommand ();}
  public static Debug       debug (final String text) {return new Debug (text);}

  public static Repeat   repeat   (final int repetitions, final TurtleCommand ... commands) {return new Repeat (repetitions, commands);}
  public static Sequence sequence (final TurtleCommand ... commands)                        {return new Sequence (commands);}

  public static InductiveReplicator<StandardRotationalTurtle> inductiveReplicator (final int copies, final TurtleCommand inductiveStep,
                                                                                   final TurtleCommand ... replicatedActions) {
    return new InductiveReplicator<StandardRotationalTurtle> (copies, inductiveStep, replicatedActions);
  }

  public static InductiveReplicator<StandardRotationalTurtle> copy (final TurtleCommand ... copiedActions) {
    return inductiveReplicator (1, pass (), sequence (copiedActions));
  }

  public static RecursiveExpansion recursiveBlock (final String name, final TurtleCommand ... body) {
    return new RecursiveExpansion (name, sequence (body));
  }

  public static RecursiveExpansion.Marker recurse (final String name, final int remainingExpansions,
                                                   final Transformation<TurtleCommand> inductiveTransformation, final TurtleCommand ... baseCommands) {
    return new RecursiveExpansion.Marker (name, remainingExpansions, inductiveTransformation, sequence (baseCommands));
  }

  public static     Scale      scale    (final double factor)                              {return new Scale (factor);}
  public static     Scale      scale    (final double factor, final boolean scaleLineSize) {return new Scale (factor, scaleLineSize);}
  public static     Identity   identity ()                                                 {return new Identity ();}
  public static <T> Compose<T> compose  (final Transformation<T> ... transformations)      {return new Compose<T> (transformations);}

  public double random ()                   {return rng.nextDouble ();}
  public double random (final double scale) {return rng.nextDouble () * scale;}
}
