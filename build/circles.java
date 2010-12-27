import cheloniidae.*;
import cheloniidae.frames.*;

import static cheloniidae.frames.CoreCommands.*;

import java.awt.Color;

public class circles extends SingleTurtleScene {
  public static void main (String[] args) {new circles ();}

  public TurtleCommand commands () {
    return section (50, 3);
  }

  public TurtleCommand section (double scale, int recursionLevel) {
    return repeat (15, move (scale),
                       turn ((recursionLevel & 1) == 0 ? 36 : -36),
                       pitch (1),
                       (recursionLevel > 0) ? section (scale / 3.0, recursionLevel - 1) : pass ());
  }
}
