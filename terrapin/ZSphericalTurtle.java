// Terrapin Turtle System copyright 2008 by Spencer Tipping
// Written 11-30-2008; licensed under LGPL, latest version

  package terrapin;

/**
 * Implements a spherical model of polar coordinates based on the Z axis.
 *
 * @author spencer
 */

  public class ZSphericalTurtle extends TurtleBase {
    // Fields
      protected double  headingTheta            = 0.0;
      protected double  headingPhi              = 0.0;

    // Accessors
      public double getHeadingTheta ()                          {return headingTheta;}
      public void   setHeadingTheta (double _headingTheta)      {headingTheta = _headingTheta;}

      public double getHeadingPhi ()                            {return headingPhi;}
      public void   setHeadingPhi (double _headingPhi)          {headingPhi = _headingPhi;}

    // Turtle information
      public Point3D computeTurtleVector (double distance) {
        return new Point3D (Math.cos (Math.toRadians (headingTheta)) *
                            Math.cos (Math.toRadians (headingPhi)) * distance,

                            Math.sin (Math.toRadians (headingTheta)) *
                            Math.cos (Math.toRadians (headingPhi)) * distance,

                            Math.sin (Math.toRadians (headingPhi)) * distance);
      }

    // Turn methods
      public ZSphericalTurtle turnTheta (double delta) {
        startMove ();
        headingTheta += delta;
        finishMove ();
        return this;
      }

      public ZSphericalTurtle turnPhi (double delta) {
        startMove ();
        headingPhi += delta;
        finishMove ();
        return this;
      }
  }
