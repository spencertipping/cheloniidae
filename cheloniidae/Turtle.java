// Cheloniidae Turtle System copyright 2006 by Spencer Tipping (all rights reserved)
// Written 07-26-2006; licensed under LGPL, latest version

package cheloniidae;

import java.awt.*;
import java.util.*;

/**
 * The Turtle class provides an object with a heading, location, color, and
 * other attributes to facilitate easy turtle graphics drawing. To use one
 * in code, see the example in {@see cheloniidae.TurtleDrawingWindow TurtleDrawingWindow}.
 *
 * @author spencer
 */

public class Turtle {
  public enum PolarAxisModel {ZSpherical, YCylindrical, OrthogonalPlanar}

  public static class TurtleState {
    public Point3D position;
    public Point3D heading;
    public TurtleState (Point3D _position, Point3D _heading) {position = _position; heading = _heading}
  }

  public Point3D position  = new Point3D ();
  public Point3D heading   = new Point3D ();

  public Color   bodyColor = new Color (0x30, 0x50, 0xA0);
  public Color   penColor  = new Color (0x30, 0xA0, 0x50);
  public boolean penDown   = true;
  public double  penSize   = 0.5;
  public int     delayPerMove            = 500;
  public boolean              visible                 = true;

  protected java.util.List<Line> lines                   = null;
    // This is the list of lines to which to add when the turtle moves.
    // It is set by the TurtleDrawingWindow and is not visible to the user.

  protected TurtleDrawingWindow  window                  = null;
    // This is the window into which to draw. It receives a notification
    // when we make a new line by a call to drawLines (false). (The false
    // means that we don't redraw all lines, but just new ones)

  protected int                 polarAxisModel          = Z_SPHERICAL;
    // This variable controls how the turtle handles phi and theta in 3D space.

  protected Stack               turtleStates            = new Stack ();

  public Turtle () {}

  // Turtle internals
  // These methods are used to change the internal state of the turtle and are not visible
  // to users.

    protected void line (double x1, double y1, double z1, double x2, double y2, double z2) {
      // Step 1: Make sure that the pen is down. If it isn't, then 
      //        all we need to do is to put in a delay.
      if (penDown) {
        // Step 2: Make sure we have a window and a line list. If
        //        we don't, then print a warning.
        if (lines != null && window != null) {
          // Add the new line to the list and prompt the turtle drawing
          // window to refresh its display.

          synchronized (lines) {
            lines.add (new Line (x1, y1, z1, x2, y2, z2, penSize, penColor));
          }

          window.updateExtents (x1, x2, y1, y2, z1, z2);
          window.enqueueGraphicsRefreshRequest (false, true);
        } else System.err.println ("Warning: Turtle does not have a graphics window!");
      }
    }

    protected void startMove () {
      // Create the delay per move here.
      if (delayPerMove > 0 && window.isVisible ())
        try {Thread.sleep (delayPerMove);}
        catch (InterruptedException e) {}
    }

    protected void finishMove () {
      // This method should be called whenever a move or turn operation is
      // completed. We tell the window to refresh its graphics.
      window.enqueueGraphicsRefreshRequest (false, true);
      Thread.yield ();
    }

  // Turtle management (available to user)
  // This is how the user interacts with the turtle. Particularly, these commands have to
  // do with the more managerial aspects of a turtle and less to do with stuff that draws
  // lines.

    public void pushTurtleState () {
      stkTurtleStates.push (new double[] {x, y, z, headingTheta, headingPhi, headingXi});
    }

    public void popTurtleState () {
      double[] state = (double[]) stkTurtleStates.pop ();

      x = state[0];
      y = state[1];
      z = state[2];
      headingTheta = state[3];
      headingPhi   = state[4];
      headingXi    = state[5];

      finishMove ();
    }

    public Turtle replicate () {
      // Returns a duplicate of this turtle. The duplicate will have been added to the window.
      Turtle result = new Turtle (x, y);

      result.setZ (z);
      result.setHeadingTheta (headingTheta);
      result.setHeadingPhi (headingPhi);
      result.setHeadingXi (headingXi);
      result.setBodyColor (bodyColor);
      result.setPenColor (penColor);
      result.setPenIsDown (penDown);
      result.setPenSize (penSize);
      result.setDelayPerMove (delayPerMove);
      result.setVisible (visible);
      result.setPolarAxisModel (polarAxisModel);

      window.add (result);

      return result;
    }

  // Turtle information (available to user)
  // Stuff that is used internally but is not so implementation-specific as to be meaningless
  // or misleading to a user.

    public Point3D computeTurtleVector (double distance) {
      // Computes the difference in location of the turtle based on the
      // distance that it would travel.
      if (polarAxisModel == Z_SPHERICAL)
        return new Point3D (Math.cos (Math.toRadians (headingTheta)) *
                            Math.cos (Math.toRadians (headingPhi)) * distance,

                            Math.sin (Math.toRadians (headingTheta)) *
                            Math.cos (Math.toRadians (headingPhi)) * distance,

                            Math.sin (Math.toRadians (headingPhi)) * distance);

      else if (polarAxisModel == Y_CYLINDRICAL)
        return new Point3D (Math.cos (Math.toRadians (headingTheta)) *
                            Math.cos (Math.toRadians (headingPhi)) * distance,

                            Math.sin (Math.toRadians (headingTheta)) * distance,

                            Math.cos (Math.toRadians (headingTheta)) *
                            Math.sin (Math.toRadians (headingPhi)) * distance);
      else if (polarAxisModel == ORTHOGONAL_PLANAR)
        return new Point3D ((Math.cos (Math.toRadians (headingTheta)) *
                             Math.cos (Math.toRadians (headingPhi)) +
                             Math.sin (Math.toRadians (headingTheta)) *
                             Math.sin (Math.toRadians (headingPhi)) *
                             Math.sin (Math.toRadians (headingXi))) * distance,

                            Math.sin (Math.toRadians (headingTheta)) *
                            Math.cos (Math.toRadians (headingXi)) * distance,

                            (Math.cos (Math.toRadians (headingTheta)) *
                             Math.sin (Math.toRadians (headingPhi)) -
                             Math.sin (Math.toRadians (headingTheta)) *
                             Math.cos (Math.toRadians (headingPhi)) *
                             Math.sin (Math.toRadians (headingXi))) * distance);
      else {
        System.err.println ("Warning: Current polar axis model is not supported.");
        return null;
      }
    }

  // Turtle movement commands (available to user)

    public Turtle move (double distance, boolean invisible) {
      // Angles should go CCW from due east as theta varies.
      startMove ();
      Point3D delta = computeTurtleVector (distance);

      if (z + delta.Z < 1.0) delta.Z = 1.0 - z;
      if (! invisible) line (x, y, z, x + delta.X, y + delta.Y, z + delta.Z);

      x += delta.X;
      y += delta.Y;
      z += delta.Z;
      finishMove ();

      return this;
    }

    public Turtle moveTo (double nx, double ny, double nz) {startMove (); line (x, y, z, nx, ny, nz);
                                                                          x = nx; y = ny; z = nz; finishMove ();
                                                            return this;}

    public Turtle jumpTo (double nx, double ny, double nz) {startMove (); x = nx; y = ny; z = nz; finishMove ();
                                                            return this;}

    public Turtle turnTheta (double angle) {startMove (); headingTheta += angle; finishMove (); return this;}
    public Turtle turnPhi   (double angle) {startMove (); headingPhi   += angle; finishMove (); return this;}
    public Turtle turnXi    (double angle) {startMove (); headingXi    += angle; finishMove (); return this;}

  // Command aliases
  // Easier to use than the above commands, and these appear quite commonly.

    public Turtle move (double distance) {return move (distance, false);}
    public Turtle jump (double distance) {return move (distance, true);}
    public Turtle turn (double angle)    {return turnTheta (angle);}

}
