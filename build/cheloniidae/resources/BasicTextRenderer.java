package cheloniidae.resources;

import cheloniidae.*;
import cheloniidae.commands.*;

import static cheloniidae.frames.CoreCommands.*;

import java.util.*;

public class BasicTextRenderer {
  public static final double diagonal = Math.sqrt (2);

  public TurtleCommand line    (final double length) {return move (length);}
  public TurtleCommand lateral (final double right)  {return sequence (turn (90), jump (right), turn (-90));}
  public TurtleCommand right   ()                    {return sequence (turn ( 45), move (diagonal), turn ( 45));}
  public TurtleCommand left    ()                    {return sequence (turn (-45), move (diagonal), turn (-45));}

  public TurtleCommand wind    (final int ... xs) {
    // 0 = change direction. By default, the direction is clockwise, or right.
    final List<TurtleCommand> cs    = new LinkedList<TurtleCommand> ();
    TurtleCommand             last  = null;
    boolean                   right = true;

    for (final int x : xs) if (x == 0) right ^= true;
                           else if (x > 0) {
                             cs.add (line (x));
                             cs.add (last = right ? right () : left ());
                           } else {
                             cs.add (jump (-x));
                             cs.add (last = right ? right () : left ());
                           }

    if (last != null) cs.remove (last);
    return seq (cs.toArray (new TurtleCommand[0]));
  }

  public TurtleCommand topLeft     () {return new Sequence (new Jump (9), new Turn (180));}
  public TurtleCommand topRight    () {return new Sequence (new Jump (9), new Turn (90), new Jump (6), new Turn (90));}
  public TurtleCommand bottomLeft  () {return new NullCommand ();}
  public TurtleCommand bottomRight () {return new Sequence (new Turn (90), new Jump (6), new Turn (-90));}

  public TurtleCommand seq (final TurtleCommand ... commands) {return new Sequence (commands);}

  public TurtleCommand drawCharacter (final char c) {
    switch (c) {
      case 'a': return seq (wind (-5, 4, 4, 4, 1, 5), lateral (3), jump (1), turn (-90));
      case 'b': return seq (topLeft (), wind (0, 8, 4, 4, 5), lateral (-6), turn (180), jump (7), turn (-90));
      case 'c': return seq (lateral (6), jump (5), left (), wind (0, 4, 4, 4), left (), jump (-1), lateral (1));
      case 'd': return seq (topRight (), wind (8, 4, 4, 5), lateral (6), jump (1), turn (-90));
      case 'e': return seq (jump (3), turn (90), wind (0, 5, 1, 4, 4, 4), left (), jump (-1), lateral (1));
      case 'f': return seq (wind (8, 4), right (), jump (3), turn (90), jump (1), line (5), lateral (-5), turn (90),
                            lateral (7));
      case 'g': return seq (jump (-2), turn (180), left (), wind (0, 4, 7, 4, 4, 5), jump (1), turn (-90));
      case 'h': return seq (topLeft (), line (9), jump (-5), turn (180), right (), wind (4, 5), turn (180),
                            lateral (1));
      case 'i': return seq (turn (90), line (6), jump (-3), turn (-90), wind (0, 5, 1), lateral (-6), turn (90),
                            lateral (5));
      case 'j': return seq (jump (-3), turn (90), wind (0, 2, 8), turn (-90), line (2), lateral (-6), turn (90),
                            lateral (5));
      case 'k': return seq (line (6), jump (-3), turn (45), line (3 * diagonal), jump (-3 * diagonal), turn (90),
                            line (3 * diagonal), turn (-135), lateral (3));
      case 'l': return seq (turn (90), line (6), jump (-3), turn (-90), wind (0, 8, 1), turn (90), jump (-9),
                            lateral (6));
      case 'm': return seq (wind (5, 1, 5), turn (180), wind (5, 1, 5), turn (180), lateral (1));
      case 'n': return seq (wind (5, 4, 5), turn (180), lateral (1));
      case 'o': return seq (jump (1), wind (4, 4, 4, 4), right (), jump (-1), lateral (7));
      case 'p': return seq (jump (-3), wind (8, 4, 4, 5), turn (90), lateral (7));
      case 'q': return seq (jump (-3), lateral (6), wind (0, 8, 4, 4, 5), turn (-90), lateral (1));
      case 'r': return seq (wind (5, 4), turn (-90), jump (-6), lateral (2));
      case 's': return seq (turn (90), wind (0, 5, 1, 0, 4, 1, 5), turn (-90), jump (-6), lateral (1));
      case 't': return seq (lateral (3), line (6), turn (90), jump (-3), line (6), lateral (6),
                            turn (-90), lateral (1));
      case 'u': return seq (jump (6), turn (180), wind (0, 5, 4, 5), jump (-6), lateral (1));
      case 'v': return seq (jump (6), turn (180), line (3), turn (-45), line (3 * diagonal), turn (-90),
                            line (3 * diagonal), turn (-45), line (3), jump (-6), lateral (1));
      case 'w': return seq (jump (6), turn (180), wind (0, 5, 1, 5), turn (180), wind (0, 5, 1, 5), jump (-6),
                            lateral (1));
      case 'x': return seq (turn (45), line (6 * diagonal), turn (-135), jump (6), turn (-135), line (6 * diagonal),
                            turn (-135), lateral (1));
      case 'y': return seq (jump (6), turn (180), wind (0, 5, 5), turn (-90), jump (6), turn (180), wind (8, 5),
                            turn (90), jump (3), lateral (7));
      case 'z': return seq (jump (6), turn (90), line (6), turn (135), line (6 * diagonal), turn (-135), line (6),
                            turn (-90), lateral (1));

      case ' ': return lateral (7);
      default:  return new NullCommand ();
    }
  }

  public Sequence drawText (final String text) {
    final List<TurtleCommand> cs = new LinkedList<TurtleCommand> ();
    for (int i = 0; i < text.length (); ++i) cs.add (drawCharacter (text.charAt (i)));
    return new Sequence (cs.toArray (new TurtleCommand[0]));
  }
}
