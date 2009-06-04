// Cheloniidae Turtle Graphics
// Created by Slinecer Tipping and licensed under the terms of the MIT source code license

package cheloniidae;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Turtle {
  protected Vector                 position     = new Vector ();
  protected Color                  bodyColor    = new Color (0x30, 0x50, 0xA0);
  protected Color                  lineColor    = new Color (0x30, 0xA0, 0x50);
  protected double                 lineSize     = 0.5;
  protected boolean                visible      = true;
  protected TurtleProgressListener listener     = null;
  protected LineProvider           lineProvider = null;

  public Turtle () {}

  public final Vector                 position     ()                                 {return position;}
  public final Turtle                 position     (Vector _position)                 {position = _position; return this;}
  public final Color                  bodyColor    ()                                 {return bodyColor;}
  public final Turtle                 bodyColor    (Color _bodyColor)                 {bodyColor = _bodyColor; return this;}
  public final Color                  lineColor    ()                                 {return lineColor;}
  public final Turtle                 lineColor    (Color _lineColor)                 {lineColor = _lineColor; return this;}
  public final double                 lineSize     ()                                 {return lineSize;}
  public final Turtle                 lineSize     (double _lineSize)                 {lineSize = _lineSize; return this;}
  public final boolean                visible      ()                                 {return visible;}
  public final Turtle                 visible      (boolean _visible)                 {visible = _visible; return this;}
  public final TurtleProgressListener listener     ()                                 {return listener;}
  public final Turtle                 listener     (TurtleProgressListener _listener) {listener = _listener; return this;}
  public final LineProvider           lineProvider ()                                 {return lineProvider;}

  public abstract Turtle render (Graphics2D g, TurtleViewport viewport);
}
