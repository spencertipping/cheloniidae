import cheloniidae.*;
import cheloniidae.frames.*;

import static cheloniidae.frames.CoreCommands.*;

public class boxes extends SingleTurtleScene {
  public static void main (String[] args) {new boxes ();}

  public TurtleCommand commands () {
    final TurtleCommand face = sequence (triangle (repeat (2, jump (100), turn (90)), jump (100)),
                                         triangle (repeat (2, jump (100), turn (90)), repeat (3, jump (100), turn (90))));

    final TurtleCommand cube = copy (repeat (4, face, jump (100), pitch (90)),
                                     turn (-90), pitch (90), face, pitch (-90), jump (-100), turn (90), jump (100),
                                     turn (90), pitch (90), face, visible (false));

    return sequence (color (0.2, 0.3, 0.4, 0.1),
                     repeat (4, cube, jump (150), cube, turn (90)));
  }
}
