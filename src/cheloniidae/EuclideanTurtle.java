package cheloniidae;

import cheloniidae.commands.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import java.util.List;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Set;

public abstract class EuclideanTurtle<T extends EuclideanTurtle>
              extends BasicTurtle<T>
           implements SupportsPosition<T>, SupportsMove<T>, SupportsJump<T>, SupportsLineSize<T>,
                      SupportsLineColor<T>, SupportsVisible<T>, TurtleCommand {

  public static class View extends ViewportCaching implements RenderAction {
    public final EuclideanTurtle turtle;
    public View (final EuclideanTurtle _turtle) {turtle = _turtle;}

    public double computeDepth (final Viewport v)
      {return v.transformPoint (turtle.position ()).length ();}

    public void render (final Viewport v) {
      final Vector tp = v.transformPoint (turtle.position ());
      final Vector td = v.transformPoint (turtle.position ().clone ().add (turtle.direction ()));
      if (tp.z > 0 && td.z > 0) {
        final double scale = 4.0 * v.scaleFactor () / tp.z;

        // pp = perceived position, pd = perceived direction.
        // These are the position and direction as seen by the user.
        final Vector pp = v.projectPoint (tp);
        final Vector pd = v.projectPoint (td);

        final Graphics2D g = v.context ();
        g.setStroke (new BasicStroke ((float) (scale / 16.0)));
        g.setColor  (turtle.color ());
        g.drawOval  ((int) (pp.x - scale), (int) (pp.y - scale), (int) (scale * 2.0), (int) (scale * 2.0));
        g.drawLine  ((int) pp.x, (int) pp.y, (int) pd.x, (int) pd.y);
      }
    }
  }

  public static class State extends BasicTurtle.State implements TurtleState, TurtleCommand {
    public final Vector position;
    public final double size;
    public final Color  color;

    public State (final Set<Attribute> _attributes, final Vector _position,
                  final double _size, final Color _color)
      {super (_attributes); position = _position.clone (); size = _size; color = _color;}

    public State applyTo (final Turtle t) {
      super.applyTo (t);
      new Sequence (new Position  (position),
                    new LineSize  (size),
                    new LineColor (color)).applyTo (t);
      return this;
    }
  }

  protected final List<CartesianLine> lines    = new ArrayList<CartesianLine> ();
  protected final View                view     = new View (this);
  protected final Vector              position = new Vector ();
  protected       double              size     = 0.25;
  protected       Color               color    = new Color (0.2f, 0.3f, 0.3f, 0.3f);
  protected       boolean             visible  = true;

  public Vector                  position  ()                       {return position;}
  public T                       position  (final Vector _position) {position.assign (_position);
                                                                     return (T) this;}
  public double                  size      ()                       {return size;}
  public T                       size      (final double _size)     {size = _size; return (T) this;}
  public double                  lineSize  ()                       {return size ();}
  public T                       lineSize  (final double _size)     {return size (_size);}
  public Color                   color     ()                       {return color;}
  public T                       color     (final Color _color)     {color = _color; return (T) this;}
  public Color                   lineColor ()                       {return color ();}
  public T                       lineColor (final Color _color)     {return color (_color);}
  public boolean                 visible   ()                       {return visible;}
  public T                       visible   (final boolean _visible) {visible = _visible; return (T) this;}

  public abstract Vector direction ();

  public T jump (final double distance) {position.addScaled (this.direction (), distance); return (T) this;}
  public T move (final double distance) {final Vector oldPosition = position.clone ();
                                         return line (oldPosition,
                                                      position.addScaled (this.direction (), distance));}
  public T line (final Vector p1, final Vector p2)
    {lines.add (new CartesianLine (p1, p2, size, color)); return (T) this;}

  public SortedSet<RenderAction> actions (final Viewport v) {
    final SortedSet<RenderAction> result = new TreeSet<RenderAction> (new PerspectiveComparator (v));
    if (visible) result.add (view);
    for (final RenderAction r : lines) if (v.shouldCancel ()) break;
                                       else                   result.add (r);
    return result;
  }

  public State serialize   ()                    {return new State (attributes, position, size, color);}
  public T     deserialize (final TurtleState t) {if (t instanceof TurtleCommand)
                                                    ((TurtleCommand) t).applyTo (this);
                                                  return (T) this;}
  public T applyTo (final Turtle t) {
    serialize ().applyTo (t);
    return (T) this;
  }

  public String toString () {return "Euclidean turtle: location = " + position ().toString () +
                                                   ", direction = " + direction ().toString ();}
}
