package cheloniidae.frames;

import cheloniidae.*;
import cheloniidae.attributes.*;
import cheloniidae.commands.*;
import cheloniidae.predicates.*;
import cheloniidae.replicators.*;
import cheloniidae.transformations.*;

import java.awt.Color;

import java.util.Random;

public abstract class CoreCommands {
  private static final Random rng = new Random ();

  public static Move move (final double distance) {return new Move (distance);}
  public static Jump jump (final double distance) {return new Jump (distance);}

  public static Turn  turn  (final double angle) {return new Turn (angle);}
  public static Bank  bank  (final double angle) {return new Bank (angle);}
  public static Pitch pitch (final double angle) {return new Pitch (angle);}

  public static LineSize  size    (final double size)     {return new LineSize (size);}
  public static LineColor color   (final Color color)     {return new LineColor (color);}
  public static Visible   visible (final boolean visible) {return new Visible (visible);}

  public static LineColor color   (final double r, final double g, final double b)
    {return color (r, g, b, 1.0);}
  public static LineColor color   (final double r, final double g, final double b, final double a)
    {return color (new Color ((float) r, (float) g, (float) b, (float) a));}

  public static Position  position  (final Vector position)  {return new Position (position);}
  public static Direction direction (final Vector direction) {return new Direction (direction);}
  public static DirectionComplement directionComplement (final Vector directionComplement)
    {return new DirectionComplement (directionComplement);}

  public static Position  position  (final double x, final double y, final double z)
    {return position  (new Vector (x, y, z));}

  public static Direction direction (final double x, final double y, final double z) 
    {return direction (new Vector (x, y, z));}

  public static DirectionComplement directionComplement (final double x, final double y, final double z)
    {return directionComplement (new Vector (x, y, z));}

  public static NullCommand pass  ()                  {return new NullCommand ();}
  public static Debug       debug (final String text) {return new Debug (text);}

  public static When     when     (final Predicate<Turtle> decisional, final TurtleCommand ... commands) {
    return new When (decisional, commands);
  }

  public static When     unless   (final Predicate<Turtle> decisional, final TurtleCommand ... commands) {
    return when (new Negation<Turtle> (decisional), commands);
  }

  public static Repeat   repeat   (final int repetitions, final TurtleCommand ... commands)
    {return new Repeat (repetitions, commands);}

  public static Sequence sequence (final TurtleCommand ... commands) {return new Sequence (commands);}

  public static Triangle triangle (final SupportsPosition p1, final SupportsPosition p2) {return new Triangle (p1, p2);}
  public static Triangle triangle (final TurtleCommand    c1, final TurtleCommand    c2) {return new Triangle (c1, c2);}

  public static InductiveReplicator<StandardRotationalTurtle> inductiveReplicator
      (final int copies, final TurtleCommand inductiveStep, final TurtleCommand ... replicatedActions) {
    return new InductiveReplicator<StandardRotationalTurtle> (copies, inductiveStep, replicatedActions);
  }

  public static InductiveReplicator<StandardRotationalTurtle> copy (final TurtleCommand ... copiedActions) {
    return inductiveReplicator (1, pass (), copiedActions);
  }

  public static BandSplitReplicator bandSplitReplicator (final TurtleCommand firstTurtlePrimer,
                                                         final TurtleCommand ... replicatedActions) {
    return new BandSplitReplicator (firstTurtlePrimer, replicatedActions);
  }

  public static RecursiveExpansion recursiveBlock (final String name, final TurtleCommand ... body) {
    return new RecursiveExpansion (name, sequence (body));
  }

  public static RecursiveExpansion.Marker recurse (final String name, final int remainingExpansions,
                                                   final Transformation<TurtleCommand> inductiveTransformation,
                                                   final TurtleCommand ... baseCommands) {
    return new RecursiveExpansion.Marker (name, remainingExpansions, inductiveTransformation, sequence (baseCommands));
  }

  public static Scale scale (final double factor)                              {return new Scale (factor);}
  public static Scale scale (final double factor, final boolean scaleLineSize) {return new Scale (factor, scaleLineSize);}
  public static Identity identity ()                                           {return new Identity ();}

  public static <T extends Transformable<T>> Compose<T> compose (final Transformation<T> ... transformations)
    {return new Compose<T> (transformations);}

  public static double random ()                                   {return rng.nextDouble ();}
  public static double random (final double scale)                 {return random () * scale;}
  public static double random (final double min, final double max) {return random (max - min) + min;}

  public static Predicate<Turtle> hasAttribute (final Predicate<Attribute> predicate) {
    return new HasAttribute (predicate);
  }

  public static AddAttribute addAttribute (final Attribute a) {return new AddAttribute (a);}
  public static Named        named        (final String name) {return new Named (name);}
  public static Pause        pause        (final long time)   {return new Pause (time);}

  // Can't make this plural because then it would atomize a sequence and not the command that we want to atomize.
  // To compensate, we could wrap each subcommand with a non-distributive proxy, but that sounds like a lot of
  // work for such a simple function.
  public static NonDistributiveProxy atomic (final TurtleCommand c) {return new NonDistributiveProxy (c);}

  public static TriangleEmitter.Start triangleStart () {return new TriangleEmitter.Start ();}
  public static TriangleEmitter.Emit  triangleEmit  () {return new TriangleEmitter.Emit  ();}
}
