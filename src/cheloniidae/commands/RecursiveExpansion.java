package cheloniidae.commands;

import cheloniidae.*;
import cheloniidae.transformations.*;

public class RecursiveExpansion implements TurtleCommand {
  public static class Marker implements TurtleCommand {
    public final String                        name;
    public final int                           remainingExpansions;
    public final Transformation<TurtleCommand> inductiveTransformation;
    public final TurtleCommand                 base;
    public       TurtleCommand                 inductiveExpansion = null;

    public Marker (final String _name, final int _remainingExpansions,
                   final Transformation<TurtleCommand> _inductiveTransformation, final TurtleCommand _base)
      {name                    = _name;                    remainingExpansions = _remainingExpansions;
       inductiveTransformation = _inductiveTransformation; base                = _base;}

    public Marker inductiveExpansion (final TurtleCommand _inductiveExpansion) {
      inductiveExpansion = _inductiveExpansion;
      return this;
    }

    public TurtleCommand applyTo (final Turtle t) {
      if (remainingExpansions <= 0) t.run (base);
      else {
        final TurtleCommand transformedExpansion =
          inductiveExpansion.map (inductiveTransformation).map (new DecrementTransformation (name));
        t.run (transformedExpansion.map (new ExpansionPopulator (name, transformedExpansion)));
      }
      return this;
    }

    public TurtleCommand map (final Transformation<TurtleCommand> t) {
      final TurtleCommand newCommand = t.transform (this);
      if (newCommand == this)
        return new Marker (name, remainingExpansions,
                           inductiveTransformation, base.map (t)).inductiveExpansion (inductiveExpansion);
      else
        return newCommand;
    }
  }

  public static class DecrementTransformation implements Transformation<TurtleCommand> {
    public final String name;
    public DecrementTransformation (final String _name) {name = _name;}

    public TurtleCommand transform (final TurtleCommand c) {
      if (c instanceof Marker && ((Marker) c).name.equals (name)) {
        final Marker cprime = (Marker) c;
        return new Marker (cprime.name, cprime.remainingExpansions - 1, cprime.inductiveTransformation, cprime.base);
      } else return c;
    }
  }

  public static class ExpansionPopulator implements Transformation<TurtleCommand> {
    public final String        name;
    public final TurtleCommand expansion;
    public ExpansionPopulator (final String _name, final TurtleCommand _expansion)
      {name = _name; expansion = _expansion;}

    public TurtleCommand transform (final TurtleCommand c) {
      if (c instanceof Marker && ((Marker) c).name.equals (name)) ((Marker) c).inductiveExpansion (expansion);
      return c;
    }
  }

  public final String        name;
  public final TurtleCommand body;

  public RecursiveExpansion (final String _name, final TurtleCommand _body) {name = _name; body = _body;}

  public TurtleCommand applyTo (final Turtle t) {
    t.run (body.map (new ExpansionPopulator (name, body)));
    return this;
  }

  public TurtleCommand map (final Transformation<TurtleCommand> t) {
    final TurtleCommand newCommand = t.transform (this);
    if (newCommand == this) return new RecursiveExpansion (name, body.map (t));
    else                    return newCommand;
  }
}
