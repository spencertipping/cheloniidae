// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae;

import java.awt.Color;

public abstract class Turtle {
  protected Point3D position  = new Point3D ();
  protected Color   bodyColor = new Color (0x30, 0x50, 0xA0);
  protected Color   penColor  = new Color (0x30, 0xA0, 0x50);
  protected double  penSize   = 0.5;
  protected boolean visible   = true;

  protected LineProvider           lineProvider = new LineProvider ();
  protected TurtleProgressListener listener     = null;

  public Turtle () {}

  public final LineProvider           lineProvider ()                             {return p;}
  public final TurtleProgressListener listener ()                                 {return listener;}
  public final Turtle                 listener (TurtleProgressListener _listener) {listener = _listener; return this;}

  protected final Turtle line (Point3D p1, Point3D p2) {
    if (lineProvider != null) lines.add (new Line (p1, p2, penSize, penColor));
    if (listener     != null) listener.turtleProgress (this);
    return this;
  }
}
