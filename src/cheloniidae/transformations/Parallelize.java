package cheloniidae.transformations;

import cheloniidae.*;
import cheloniidae.predicates.*;

public class Parallelize implements Transformation<TurtleCommand> {
  // Sometimes the order of execution of turtle commands matters. One example of this is when they're operating in parallel constructing triangles using a
  // pathwise triangle connector. In this case, each turtle should move one step and then the emitter should be run. However, normally serial turtle command
  // compositions distribute across turtle groups such as connectors. To fix this, we wrap those commands inside of non-distributive proxies.
  //
  // There's a slight catch here. When we grab a serial composition, we need to transform the original. Well, we can't just run it back through this
  // transformation, since that would result in an infinite loop. Instead, we create a new child instance of this transformation that is primed to ignore the
  // thing we're wrapping. This is why we have a private constructor.

  private final TurtleCommand commandBeingWrapped;

  public  Parallelize ()                                         {commandBeingWrapped = null;}
  private Parallelize (final TurtleCommand _commandBeingWrapped) {commandBeingWrapped = _commandBeingWrapped;}

  public TurtleCommand transform (final TurtleCommand c) {
    if (c instanceof SerialTurtleCommandComposition && c != commandBeingWrapped) return new NonDistributiveProxy (new Parallelize (c).transform (c));
    else                                                                         return c;
  }
}
