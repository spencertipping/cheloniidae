import cheloniidae.*;
import cheloniidae.frames.*;

import static cheloniidae.frames.CoreCommands.*;

public class boxes extends SingleTurtleScene {
  public static void main (String[] args) {new solidgasket ();}

  public TurtleCommand commands () {
    final TurtleCommand face = sequence (triangle (repeat (2, jump (100), turn (90)), jump (100)),
                                         triangle (repeat (2, jump (100), turn (90)), repeat (3, jump (100), turn (90))));

    final TurtleCommand cube = copy (repeat (4, face, jump (100), pitch (90)),
                                     turn (-90), pitch (90), face, pitch (-90), jump (-100), turn (90), jump (100),
                                     turn (90), pitch (90), face, visible (false));

    return sequence (color (new java.awt.Color (0.2f, 0.3f, 0.4f, 0.1f)),
                            repeat (4, cube, jump (150), cube, turn (90)));
  }
}
