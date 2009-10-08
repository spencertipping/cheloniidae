import cheloniidae.*;
import cheloniidae.frames.*;

public class solidgasket extends SingleTurtleScene {
  public static void main (String[] args) {new solidgasket ();}

  public TurtleCommand commands () {
    final TurtleCommand face = sequence (triangle (repeat (2, jump (100), turn (90)), jump (100)),
                                         triangle (repeat (2, jump (100), turn (90)), repeat (3, jump (100), turn (90))));

    final TurtleCommand cube = copy (repeat (4, face, jump (100), pitch (90)),
                                     bank (-90), face,
                                     repeat (2, jump (100), pitch (90)), face, visible (false));

    final TurtleCommand recursiveInvocation = recurse ("solidgasket", 1, scale (1.0 / 3.0, true), cube);

    return sequence (color (new java.awt.Color (0.2f, 0.3f, 0.4f, 0.01f)),
                     recursiveBlock ("solidgasket",
                                    copy (inductiveReplicator (4, sequence (jump (200), pitch (90), jump (100)),
                                                                  turn (90),
                                                                  repeat (2, turn (-90), recursiveInvocation, jump (100), recursiveInvocation),
                                                                  turn (-90), recursiveInvocation))));
  }
}
