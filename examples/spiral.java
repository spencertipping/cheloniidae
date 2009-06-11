import cheloniidae.*;
import cheloniidae.frames.*;

public class spiral extends SingleTurtleScene {
  public static void main (String[] args) {
    new spiral ();
  }

  public TurtleCommand run () {
    return repeat (5760, pitch (0.25),
                         move (10.0),
                         pitch (-0.25),
                         turn (4.0));
  }
}
