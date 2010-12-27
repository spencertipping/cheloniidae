package cheloniidae;

import java.io.Serializable;

public final class Vector implements Serializable, Cloneable, Comparable<Vector> {
  public double x = 0.0;
  public double y = 0.0;
  public double z = 0.0;

  public Vector ()                                               {}
  public Vector (final Vector existing)                          {this (existing.x, existing.y, existing.z);}
  public Vector (final double all)                               {this (all, all, all);}
  public Vector (final double x, final double y, final double z) {this.assign (x, y, z);}

  public Vector clone     ()                   {return new Vector (this);}
  public int    compareTo (final Vector other) {return Double.compare (lengthSquared (), other.lengthSquared ());}
  public String toString  ()                   {return "<" + Double.toString (x) + ", " +
                                                             Double.toString (y) + ", " +
                                                             Double.toString (z) + ">";}

  public Vector assign (final Vector v)                                    {return this.assign (v.x, v.y, v.z);}
  public Vector assign (final double nx, final double ny, final double nz) {x = nx; y = ny; z = nz; return this;}
  public Vector center ()                                                  {return this.assign (0, 0, 0);}

  public Vector addScaled (final Vector other, final double factor) {x += other.x * factor;
                                                                     y += other.y * factor;
                                                                     z += other.z * factor;
                                                                     return this;}

  public Vector add      (final Vector other)  {x += other.x; y += other.y; z += other.z; return this;}
  public Vector subtract (final Vector other)  {x -= other.x; y -= other.y; z -= other.z; return this;}
  public Vector multiply (final Vector other)  {x *= other.x; y *= other.y; z *= other.z; return this;}
  public Vector multiply (final double factor) {x *= factor;  y *= factor;  z *= factor;  return this;}
  public Vector divide   (final Vector other)  {x /= other.x; y /= other.y; z /= other.z; return this;}
  public Vector divide   (final double factor) {x /= factor;  y /= factor;  z /= factor;  return this;}

  public Vector componentwiseMinimum (final Vector other) {return this.assign (other.x < this.x ? other.x : this.x,
                                                                               other.y < this.y ? other.y : this.y,
                                                                               other.z < this.z ? other.z : this.z);}
  public Vector componentwiseMaximum (final Vector other) {return this.assign (other.x > this.x ? other.x : this.x,
                                                                               other.y > this.y ? other.y : this.y,
                                                                               other.z > this.z ? other.z : this.z);}

  public double dot (final Vector other) {return x * other.x + y * other.y + z * other.z;}
  public double length ()                {return Math.sqrt (x*x + y*y + z*z);}
  public double lengthSquared ()         {return x*x + y*y + z*z;}

  public Vector proj (final Vector base) {return new Vector (base).multiply (this.dot (base) / base.lengthSquared ());}
  public Vector orth (final Vector base) {return this.proj (base).multiply (-1.0).add (this);}

  public Vector normalize () {return divide (length ());}

  public double distanceFrom (final Vector other) {return Math.sqrt ((x - other.x) * (x - other.x) +
                                                                     (y - other.y) * (y - other.y) +
                                                                     (z - other.z) * (z - other.z));}

  public Vector cross (final Vector other) {return new Vector (y * other.z - z * other.y,
                                                               z * other.x - x * other.z,
                                                               x * other.y - y * other.x);}

  public Vector toCoordinateSpace (final Vector v1, final Vector v2, final Vector v3)
    {return new Vector (this.dot (v1) / v1.length (), this.dot (v2) / v2.length (), this.dot (v3) / v3.length ());}

  public Vector fromCoordinateSpace (final Vector v1, final Vector v2, final Vector v3)
    {return new Vector (v1).multiply (x).addScaled (v2, y).addScaled (v3, z);}

  public Vector rotatedAbout (final Vector v, final double degrees) {
    final Vector b1 = new Vector (v).normalize ();
    final Vector b2 = this.orth (b1).normalize ();
    final Vector b3 = b1.cross (b2);                  // The cross product of orthogonal unit vectors is a unit vector.
    final double l  = this.orth (b1).length ();

    return this.proj (b1).addScaled (b2, Math.cos (degrees * Math.PI / 180.0) * l).
                          addScaled (b3, Math.sin (degrees * Math.PI / 180.0) * l);
  }
}
