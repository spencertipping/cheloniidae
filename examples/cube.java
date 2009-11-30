import cheloniidae.*;
import cheloniidae.frames.*;

import static cheloniidae.frames.CoreCommands.*;

public class cube extends SingleTurtleScene {
  public static void main (String[] args) {new cube ();}
  
  public TurtleCommand commands () {
    return sequence (repeat (4,
                             triangle (repeat (2, move (100), turn (90)), move (100)),
                             triangle (repeat (2, move (100), turn (90)), repeat (3, move (100), turn (90))),
                             move (100), pitch (90)),
                     bank (90),
                     triangle (repeat (2, move (100), turn (90)), move (100)),
                     triangle (repeat (2, move (100), turn (90)), repeat (3, move (100), turn (90))),
                     repeat (2, move (100), pitch (-90)),
                     triangle (repeat (2, move (100), turn (90)), move (100)),
                     triangle (repeat (2, move (100), turn (90)), repeat (3, move (100), turn (90))));
  }
}
