package cheloniidae;

import cheloniidae.commands.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import java.util.List;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public abstract class EuclideanTurtle<T extends EuclideanTurtle>
implements Turtle<T>, SupportsMove<T>, SupportsJump<T>, SupportsLineSize<T>, SupportsLineColor<T>, TurtleCommand {

  public static class View extends ViewportCaching implements RenderAction {
    public final EuclideanTurtle turtle;
    public View (EuclideanTurtle _turtle) {turtle = _turtle;}

    public double computeDepth (Viewport v) {return v.transformPoint (turtle.position ()).length ();}

    public void render (Viewport v) {
      Vector pp = v.transformPoint (turtle.position ());
      Vector pd = v.transformPoint (new Vector (turtle.position ()).add (turtle.direction ()));
      if (pp.z > 0 && pd.z > 0) {
        final double scale = 4.0 * v.scaleFactor () / pp.z;

        pp = v.projectPoint (pp);
        pd = v.projectPoint (pd);

        Graphics2D g = v.context ();
        g.setStroke (new BasicStroke ((float) (scale / 16.0)));
        g.setColor  (turtle.color ());
        g.drawOval  ((int) (pp.x - scale), (int) (pp.y - scale), (int) (scale * 2.0), (int) (scale * 2.0));
        g.drawLine  ((int) pp.x,           (int) pp.y,           (int) pd.x,          (int) pd.y);
      }
    }
  }

  public static class State implements TurtleState, TurtleCommand {
    public final Vector position;
    public final double size;
    public final Color  color;

    public State (Vector _position, double _size, Color _color)
      {position = _position.clone (); size = _size; color = _color;}

    public State applyTo (Turtle t) {
      new CommandSequence (new Position  (position),
                           new LineSize  (size),
                           new LineColor (color)).applyTo (t);
      return this;
    }
  }

  protected final List<CartesianLine> lines    = new ArrayList<CartesianLine> ();
  protected final View                view     = new View (this);
  protected final Vector              position = new Vector ();
  protected       double              size     = 0.25;
  protected       Color               color    = new Color (0.5f, 0.65f, 0.55f, 0.5f);

  public Vector                  position  ()                 {return position;}
  public T                       position  (Vector _position) {position.assign (_position); return (T) this;}
  public double                  size      ()                 {return size;}
  public T                       size      (double _size)     {size = _size; return (T) this;}
  public T                       lineSize  (double _size)     {return size (_size);}
  public Color                   color     ()                 {return color;}
  public T                       color     (Color _color)     {color = _color; return (T) this;}
  public T                       lineColor (Color _color)     {return color (_color);}

  public abstract Vector direction ();

  public T line (Vector p1, Vector p2) {lines.add (new CartesianLine (p1, p2, size, color)); return (T) this;}
  public T jump (double distance)      {position.addScaled (this.direction (), distance); return (T) this;}
  public T move (double distance)      {Vector oldPosition = position.clone ();
                                        return line (oldPosition, position.addScaled (this.direction (), distance));}

  public SortedSet<RenderAction> actions (Viewport v) {
    final SortedSet<RenderAction> result = new TreeSet<RenderAction> (new PerspectiveComparator (v));
    result.add (view);
    result.addAll (lines);
    return result;
  }

  public State serialize   ()              {return new State (position, size, color);}
  public T     deserialize (TurtleState t) {if (t instanceof TurtleCommand) ((TurtleCommand) t).applyTo (this);
                                            else System.err.println ("Class of type " + t.getClass ().getName () + " received."); return (T) this;}

  public T applyTo (Turtle t) {
    serialize ().applyTo (t);
    return (T) this;
  }

  public T run (TurtleCommand c) {
    c.applyTo (this);
    return (T) this;
  }
}
