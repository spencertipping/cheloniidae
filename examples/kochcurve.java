import cheloniidae.*;
import cheloniidae.frames.*;

import java.awt.*;

public class kochcurve extends SingleTurtleScene {
  public static void main (String [] args) {
    new kochcurve ();
  }

  public TurtleCommand run () {
    return repeat (3, curve (400, 5),
                      turn (120));
  }

  public TurtleCommand curve (double distance, int recursionlevel) {
    if (recursionlevel > 0) {
      return sequence (
        curve (distance / 3.0, recursionlevel - 1),
        turn (-60),
        curve (distance / 3.0, recursionlevel - 1),
        turn (120),
        curve (distance / 3.0, recursionlevel - 1),
        turn (-60),
        curve (distance / 3.0, recursionlevel - 1));
    } else return move (distance);
  }
}
