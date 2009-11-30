import cheloniidae.*;
import cheloniidae.frames.*;

import static cheloniidae.frames.CoreCommands.*;

import java.awt.*;

public class spheres extends SingleTurtleScene {
  public static void main (String[] args) {
    new spheres ();
  }

  public TurtleCommand commands () {
    return sequence (size (0.1), color (new Color (0.3f, 0.5f, 0.5f, 0.8f)), spheres (10));
  }

  public TurtleCommand spheres (int size) {
    return sequence (repeat (35, repeat (72, move (8 - size * 0.5), pitch (5)),
                                 turn (3)),
                     turn (-30),
                     bank (8),
                     size > 0 ? spheres (size - 1) : pass ());
  }
}
