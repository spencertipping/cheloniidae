package cheloniidae.transformations;

import cheloniidae.*;

public class Identity implements Transformation<TurtleCommand> {
  public Identity () {}
  public TurtleCommand transform (final TurtleCommand c) {return c;}
}
