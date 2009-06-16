import cheloniidae.*;
import cheloniidae.frames.*;

import java.util.Random;

public class tree extends SingleTurtleScene {
  public static void main (String[] args) {
    new tree ();
  }

  public TurtleCommand run () {
    return tree (3, 10);
  }

  public TurtleCommand tree (int recursionLevel, double scale) {
    return repeat (2,
      stack.push (),
      bank (new Random ().nextDouble () * 360.0),
      turn (new Random ().nextDouble () * 20.0 - 10.0),
      move (scale),
      (recursionLevel > 0) ? tree (recursionLevel - 1, scale * new Random ().nextDouble () * 0.9) : pass (),
      stack.pop ());
  }
}
