import cheloniidae.*;
import cheloniidae.frames.*;

public class pyramid extends SingleTurtleScene {
  public static void main (String[] args) {new pyramid ();}

  public TurtleCommand commands () {
    final StandardRotationalTurtle c1 = new StandardRotationalTurtle ();
    final StandardRotationalTurtle c2 = new StandardRotationalTurtle ().move (100);
    final StandardRotationalTurtle c3 = new StandardRotationalTurtle ().turn (60).move (100);
    return sequence (triangle (c2, c3),
                     turn (30), pitch (-60), move (100), triangle (c1, c2), triangle (c2, c3), triangle (c1, c3));
  }
}
