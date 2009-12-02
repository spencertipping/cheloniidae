import cheloniidae.*;
import cheloniidae.frames.*;
import static cheloniidae.frames.CoreCommands.*;

public class triangle extends SingleTurtleScene {
  public static void main (String[] args) {new triangle();}

  public TurtleCommand commands () {
    return triangle (move (100), sequence (turn (60), move (100)));
  }
}
