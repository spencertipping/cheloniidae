package cheloniidae.commands;

import cheloniidae.*;
import cheloniidae.transformations.*;

public class RecursiveExpansion implements NonDistributiveTurtleCommand {
  public static class RecursiveMarker extends NullCommand {
    public final String                        name;
    public final int                           remainingExpansions;
    public final Transformation<TurtleCommand> inductiveTransformation;
    public final TurtleCommand                 base;

    public RecursiveMarker (String _name, int _remainingExpansions, Transformation<TurtleCommand> _inductiveTransformation, TurtleCommand _base)
      {name = _name; remainingExpansions = _remainingExpansions; inductiveTransformation = _inductiveTransformation; base = _base;}
  }

  public static class RecursiveExpansionDecrementTransformation implements Transformation<TurtleCommand> {
    public final String name;
    public RecursiveExpansionDecrementTransformation (String _name) {name = _name;}

    public TurtleCommand transform (TurtleCommand c) {
      if (c instanceof RecursiveMarker && ((RecursiveMarker) c).name.equals (name)) {
        RecursiveMarker cprime = (RecursiveMarker) c;
        return new RecursiveMarker (cprime.name, cprime.remainingExpansions - 1, cprime.inductiveTransformation, cprime.base);
      } else return c;
    }
  }

  public static class RecursiveExpansionTransformation implements Transformation<TurtleCommand> {
    public final String                                    name;
    public final TurtleCommand                             expansion;
    public final RecursiveExpansionDecrementTransformation decrement;

    public RecursiveExpansionTransformation (String _name, TurtleCommand _expansion) {
      name      = _name;
      expansion = _expansion;
      decrement = new RecursiveExpansionDecrementTransformation (name);
    }

    public TurtleCommand transform (TurtleCommand c) {
      if (c instanceof RecursiveMarker && ((RecursiveMarker) c).name.equals (name)) {
        RecursiveMarker cprime = (RecursiveMarker) c;
        return (cprime.remainingExpansions > 0) ? expansion.map (new Compose<TurtleCommand> (cprime.inductiveTransformation, decrement)).map (this) :
                                                  cprime.base;
      } else if (c instanceof RecursiveExpansion && ((RecursiveExpansion) c).name.equals (name))
        return ((RecursiveExpansion) c).body.map (this);
      else return c;
    }
  }

  public final String        name;
  public final TurtleCommand body;

  public RecursiveExpansion (String _name, TurtleCommand _body) {name = _name; body = _body;}

  public TurtleCommand applyTo (Turtle t) {
    t.run (body.map (new RecursiveExpansionTransformation (name, this)));
    return this;
  }

  public TurtleCommand map (Transformation<TurtleCommand> c) {
    TurtleCommand newExpansion = c.transform (this);
    if (newExpansion == this) return new RecursiveExpansion (name, body.map (c));
    else                      return newExpansion;
  }
}
