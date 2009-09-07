package cheloniidae.transformations;

import cheloniidae.*;

public class Identity implements Transformation<TurtleCommand> {
  public Identity () {}
  public TurtleCommand transform (TurtleCommand c) {return c;}
}
