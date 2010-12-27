import cheloniidae.*;
import cheloniidae.resources.*;
import cheloniidae.frames.*;

import static cheloniidae.frames.CoreCommands.*;

import java.util.*;

public class text extends SingleTurtleScene {
  public static void main (String[] args) {new text ();}

  public TurtleCommand commands () {
    final double            lineHeight = 20;
    final BasicTextRenderer t          = new BasicTextRenderer ();
    return sequence (size (1), turn (180), bank (180),
                     copy (jump (-lineHeight * 4), turn (1),
                           repeat (500, t.drawText ("cheloniidae ").interspersing (sequence (turn (-1), bank (1), turn (1))))),
                     color (0, 0, 0, 0.5),
                     copy (t.drawText ("welcome to cheloniidae").interspersing (sequence (pitch (15), turn (90), jump (2), turn (-84)))),
                     copy (jump (-lineHeight * 1), t.drawText ("this is what happens when you start with turtle graphics").interspersing (turn (1))),
                     copy (jump (-lineHeight * 2), t.drawText ("and have too much free time").interspersing (bank (4))));
  }
}
