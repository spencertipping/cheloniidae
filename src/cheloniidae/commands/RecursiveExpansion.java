package cheloniidae.commands;

import cheloniidae.*;
import cheloniidae.transformations.*;

public class RecursiveExpansion implements NonDistributiveTurtleCommand {
  public static class Marker implements NonDistributiveTurtleCommand {
    public final String                        name;
    public final int                           remainingExpansions;
    public final Transformation<TurtleCommand> inductiveTransformation;
    public final TurtleCommand                 base;
    public       TurtleCommand                 inductiveExpansion = null;

    public Marker (String _name, int _remainingExpansions, Transformation<TurtleCommand> _inductiveTransformation, TurtleCommand _base)
      {name = _name; remainingExpansions = _remainingExpansions; inductiveTransformation = _inductiveTransformation; base = _base;}

    public Marker inductiveExpansion (TurtleCommand _inductiveExpansion) {
      inductiveExpansion = _inductiveExpansion;
      return this;
    }

    public TurtleCommand applyTo (Turtle t) {
      if (remainingExpansions <= 0) t.run (base);
      else {
        TurtleCommand transformedExpansion = inductiveExpansion.map (inductiveTransformation).map (new DecrementTransformation (name));
        t.run (transformedExpansion.map (new ExpansionPopulator (name, transformedExpansion)));
      }
      return this;
    }

    public TurtleCommand map (Transformation<TurtleCommand> t) {
      TurtleCommand newCommand = t.transform (this);
      if (newCommand == this) return new Marker (name, remainingExpansions, inductiveTransformation, base.map (t)).inductiveExpansion (inductiveExpansion);
      else                    return newCommand;
    }
  }

  public static class DecrementTransformation implements Transformation<TurtleCommand> {
    public final String name;
    public DecrementTransformation (String _name) {name = _name;}

    public TurtleCommand transform (TurtleCommand c) {
      if (c instanceof Marker && ((Marker) c).name.equals (name)) {
        Marker cprime = (Marker) c;
        return new Marker (cprime.name, cprime.remainingExpansions - 1, cprime.inductiveTransformation, cprime.base);
      } else return c;
    }
  }

  public static class ExpansionPopulator implements Transformation<TurtleCommand> {
    public final String        name;
    public final TurtleCommand expansion;
    public ExpansionPopulator (String _name, TurtleCommand _expansion) {name = _name; expansion = _expansion;}

    public TurtleCommand transform (TurtleCommand c) {
      if (c instanceof Marker && ((Marker) c).name.equals (name)) ((Marker) c).inductiveExpansion (expansion);
      return c;
    }
  }

  public final String        name;
  public final TurtleCommand body;

  public RecursiveExpansion (String _name, TurtleCommand _body) {name = _name; body = _body;}

  public TurtleCommand applyTo (Turtle t) {
    t.run (body.map (new ExpansionPopulator (name, body)));
    return this;
  }

  public TurtleCommand map (Transformation<TurtleCommand> t) {
    TurtleCommand newCommand = t.transform (this);
    if (newCommand == this) return new RecursiveExpansion (name, body.map (t));
    else                    return newCommand;
  }
}
