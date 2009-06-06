// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae;

import java.awt.Color;
import java.awt.Graphics2D;

public interface TurtleDriver {
  public Vector                 position     ();
  public TurtleDriver           position     (Vector _position);
  public Color                  bodyColor    ();
  public TurtleDriver           bodyColor    (Color _bodyColor);
  public Color                  lineColor    ();
  public TurtleDriver           lineColor    (Color _lineColor);
  public double                 lineSize     ();
  public TurtleDriver           lineSize     (double _lineSize);
  public boolean                visible      ();
  public TurtleDriver           visible      (boolean _visible);
  public TurtleProgressListener listener     ();
  public TurtleDriver           listener     (TurtleProgressListener _listener);
  public LineProvider           lineProvider ();

  public Turtle render (Graphics2D g, TurtleViewport viewport);

  public TurtleState  serialize   ();
  public TurtleDriver deserialize (TurtleState t);
}
