// Terrapin Turtle System copyright 2008 by Spencer Tipping
// Written 11-30-2008; licensed under LGPL, latest version

  package terrapin;

/**
 * Implements an orthogonal planar model of polar coordinates. See the included
 * documentation PDF for details about what this means.
 *
 * @author spencer
 */

  public class OrthogonalPlanarTurtle extends TurtleBase {
    // Fields
      protected double  headingTheta            = 0.0;
      protected double  headingPhi              = 0.0;
      protected double  headingXi               = 0.0;

    // Accessors
      public double getHeadingTheta ()                          {return headingTheta;}
      public void   setHeadingTheta (double _headingTheta)      {headingTheta = _headingTheta;}

      public double getHeadingPhi ()                            {return headingPhi;}
      public void   setHeadingPhi (double _headingPhi)          {headingPhi = _headingPhi;}

      public double getHeadingXi ()                             {return headingXi;}
      public void   setHeadingXi (double _headingXi)            {headingXi = _headingXi;}

    // Turtle information
      public Point3D computeTurtleVector (double distance) {
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
      }

    // Turn methods
      public OrthogonalPlanarTurtle turnTheta (double delta) {
        startMove ();
        headingTheta += delta;
        finishMove ();
        return this;
      }

      public OrthogonalPlanarTurtle turnPhi (double delta) {
        startMove ();
        headingPhi += delta;
        finishMove ();
        return this;
      }

      public OrthogonalPlanarTurtle turnXi (double delta) {
        startMove ();
        headingXi += delta;
        finishMove ();
        return this;
      }
  }
