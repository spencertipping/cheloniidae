// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class DistributiveRotationalCartesianDriver extends ArrayList<RotationalCartesianDriver> implements RotationalCartesianDriver {
  // Predicates are used to select subsets of the turtles. Often they will have some form of state; hence,
  // a reset() method is used. reset() will be invoked before a subset is selected.
  public static interface Predicate {
    public void    reset   ();
    public boolean accepts (RotationalCartesianDriver d);
  }

  public static class State extends ArrayList<TurtleState> implements TurtleState {}

  public Vector                                directionComplement ()                            {Vector result = new Vector ();
                                                                                                  for (RotationalCartesianDriver d : this) result.add (d.directionComplement ());
                                                                                                  return result.divide (this.size ());}
  public DistributiveRotationalCartesianDriver directionComplement (Vector _directionComplement) {for (RotationalCartesianDriver d : this) d.directionComplement (_directionComplement);
                                                                                                  return this;}

  public DistributiveRotationalCartesianDriver move  (double distance) {for (RotationalCartesianDriver d : this) d.move  (distance); return this;}
  public DistributiveRotationalCartesianDriver jump  (double distance) {for (RotationalCartesianDriver d : this) d.jump  (distance); return this;}
  public DistributiveRotationalCartesianDriver pitch (double distance) {for (RotationalCartesianDriver d : this) d.pitch (distance); return this;}
  public DistributiveRotationalCartesianDriver bank  (double distance) {for (RotationalCartesianDriver d : this) d.bank  (distance); return this;}
  public DistributiveRotationalCartesianDriver turn  (double distance) {for (RotationalCartesianDriver d : this) d.turn  (distance); return this;}

  public Vector                                position  ()                                 {return null;}
  public DistributiveRotationalCartesianDriver position  (Vector _position)                 {for (RotationalCartesianDriver d : this) d.position (_position); return this;}
  public Color                                 bodyColor ()                                 {return null;}
  public DistributiveRotationalCartesianDriver bodyColor (Color _bodyColor)                 {for (RotationalCartesianDriver d : this) d.bodyColor (_bodyColor); return this;}
  public Color                                 lineColor ()                                 {return null;}
  public DistributiveRotationalCartesianDriver lineColor (Color _lineColor)                 {for (RotationalCartesianDriver d : this) d.lineColor (_lineColor); return this;}
  public double                                lineSize  ()                                 {return 0.0;}
  public DistributiveRotationalCartesianDriver lineSize  (double _lineSize)                 {for (RotationalCartesianDriver d : this) d.lineSize (_lineSize); return this;}
  public boolean                               visible   ()                                 {return true;}
  public DistributiveRotationalCartesianDriver visible   (boolean _visible)                 {for (RotationalCartesianDriver d : this) d.visible (_visible); return this;}
  public TurtleProgressListener                listener  ()                                 {return null;}
  public DistributiveRotationalCartesianDriver listener  (TurtleProgressListener _listener) {for (RotationalCartesianDriver d : this) d.listener (_listener); return this;}

  public UnionLineProvider lineProvider () {
    UnionLineProvider result = new UnionLineProvider ();
    for (RotationalCartesianDriver d : this) result.add (d.lineProvider ());
    return result;
  }

  public DistributiveRotationalCartesianDriver render (Graphics2D g, TurtleViewport v)
    {for (RotationalCartesianDriver d : this) d.render (g, v); return this;}

  public State serialize () {
    State s = new State ();
    for (RotationalCartesianDriver d : this) s.add (d.serialize ());
    return s;
  }

  public DistributiveRotationalCartesianDriver deserialize (TurtleState s) {
    if (s instanceof State) for (int i = 0; i < this.size (); ++i) this.get (i).deserialize (((State) s).get (i));
    return this;
  }
}
