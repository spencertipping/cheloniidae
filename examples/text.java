import cheloniidae.*;
import cheloniidae.resources.*;
import cheloniidae.frames.*;

import static cheloniidae.frames.CoreCommands.*;

import java.util.*;

public class text extends SingleTurtleScene {
  public static void main (String[] args) {new text ();}

  public TurtleCommand commands () {
    BasicTextRenderer t = new BasicTextRenderer ();
    return sequence (size (1), t.drawText ("public static void main (String[] args) {new text ();}").interspersing (sequence (pitch (15), turn (90), jump (2), turn (-84))));
  }
}
