import cheloniidae.*;
import cheloniidae.frames.*;

import java.util.Random;

public class tree extends SingleTurtleScene {
  public static void main (String[] args) {
    new tree ();
  }

  public TurtleCommand run () {
    return tree (4, 20);
  }

  public TurtleCommand tree (int recursionLevel, double scale) {
    return sequence (
      stack.push (),
      new TurtleCommand () {
        public TurtleCommand applyTo (Turtle t) {
          System.out.println (((EuclideanTurtle) t).position ().toString ());
          return this;
        }
      },
      bank (random () * 360.0),
      stack.push (),
      turn (random () * 20.0),
      move (scale),
      (recursionLevel > 0) ? tree (recursionLevel - 1, scale * (random () * 0.4 + 0.5)) : pass (),
      stack.pop (),
      new TurtleCommand () {
        public TurtleCommand applyTo (Turtle t) {
          System.out.println (((EuclideanTurtle) t).position ().toString ());
          return this;
        }
      },
      stack.push (),
      turn (-random () * 40.0),
      move (scale),
      (recursionLevel > 0) ? tree (recursionLevel - 1, scale * (random () * 0.4 + 0.5)) : pass (),
      stack.pop (),
      stack.pop ());
  }
}
