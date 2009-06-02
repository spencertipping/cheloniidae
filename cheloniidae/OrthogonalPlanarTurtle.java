// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae;

public class OrthogonalPlanarTurtle extends EuclideanTurtle {
  protected Vector angles = new Vector ();

  public OrthogonalPlanarTurtle (EuclideanTurtle _turtle) {turtle = _turtle;}

  public final double                 theta  ()               {return angles.x;}
  public final OrthogonalPlanarTurtle theta  (double _theta)  {angles.x = _theta; return this;}
  public final double                 phi    ()               {return angles.y;}
  public final OrthogonalPlanarTurtle phi    (double _phi)    {angles.y = _phi; return this;}
  public final double                 xi     ()               {return angles.z;}
  public final OrthogonalPlanarTurtle xi     (double _xi)     {angles.z = _xi; return this;}

  public final Vector                 angles ()               {return angles;}
  public final OrthogonalPlanarTurtle angles (Vector _angles) {angles = _angles; return this;}

  public final Vector direction () {
    double cosTheta = Math.cos (theta ());
    double sinTheta = Math.sin (theta ());
    double cosPhi   = Math.cos (phi ());
    double sinPhi   = Math.sin (phi ());
    double sinXi    = Math.sin (xi ());
    return new Vector (cosTheta * cosPhi + sinTheta * sinPhi * sinXi,
                       sinTheta * Math.cos (xi ()),
                       cosTheta * sinPhi - sinTheta * cosPhi * sinXi);
  }
}
