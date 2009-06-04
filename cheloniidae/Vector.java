// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae;

import java.io.Serializable;

/**
 * This class implements a point or vector in three-dimensional space.
 * @author Spencer Tipping
 */

public final class Vector implements Serializable {
  public double x = 0.0;
  public double y = 0.0;
  public double z = 0.0;

  public Vector ()                             {}
  public Vector (Vector existing)              {this (existing.x, existing.y, existing.z);}
  public Vector (double all)                   {this (all, all, all);}
  public Vector (double x, double y, double z) {this.assign (x, y, z);}

  public synchronized final Vector assign (Vector value)                    {return this.assign (value.x, value.y, value.z);}
  public synchronized final Vector assign (double nx, double ny, double nz) {x = nx; y = ny; z = nz; return this;}
  public synchronized final Vector center ()                                {return this.assign (0, 0, 0);}


  public synchronized final Vector addScaled (Vector other, double factor) {x += other.x * factor;
                                                                            y += other.y * factor;
                                                                            z += other.z * factor;
                                                                            return this;}

  public synchronized final Vector add      (Vector other)  {x += other.x; y += other.y; z += other.z; return this;}
  public synchronized final Vector subtract (Vector other)  {x -= other.x; y -= other.y; z -= other.z; return this;}
  public synchronized final Vector multiply (Vector other)  {x *= other.x; y *= other.y; z *= other.z; return this;}
  public synchronized final Vector multiply (double factor) {x *= factor;  y *= factor;  z *= factor;  return this;}
  public synchronized final Vector divide   (Vector other)  {x /= other.x; y /= other.y; z /= other.z; return this;}
  public synchronized final Vector divide   (double factor) {x /= factor;  y /= factor;  z /= factor;  return this;}

  public synchronized final double dot (Vector other) {return x * other.x + y * other.y + z * other.z;}
  public synchronized final double length ()          {return Math.sqrt (x*x + y*y + z*z);}
  public synchronized final double lengthSquared ()   {return x*x + y*y + z*z;}

  public synchronized final Vector proj (Vector base) {return new Vector (base).multiply (this.dot (base) / base.lengthSquared ());}
  public synchronized final Vector orth (Vector base) {return this.proj (base).addScaled (this, -1.0);}

  public synchronized final Vector normalize () {return divide (length ());}

  public synchronized final double distanceFrom (Vector other) {return Math.sqrt ((x - other.x) * (x - other.x) +
                                                                                  (y - other.y) * (y - other.y) +
                                                                                  (z - other.z) * (z - other.z));}

  public synchronized final Vector cross (Vector other) {return new Vector (y * other.z - z * other.y,
                                                                            z * other.x - x * other.z,
                                                                            x * other.y - y * other.x);}

  public synchronized final String toString () {return "<" + Double.toString (x) + ", " + Double.toString (y) + ", " + Double.toString (z) + ">";}

  public synchronized final Vector toCoordinateSpace (Vector v1, Vector v2, Vector v3)
    {return new Vector (this.dot (v1) / v1.length (), this.dot (v2) / v2.length (), this.dot (v3) / v3.length ());}

  public synchronized final Vector fromCoordinateSpace (Vector v1, Vector v2, Vector v3)
    {return new Vector (v1).multiply (x).addScaled (v2, y).addScaled (v3, z);}

  public synchronized final Vector rotateAbout (Vector v, double angle) {
    Vector b1 = new Vector (v).normalize ();
    Vector b2 = this.orth (b1).normalize ();
    Vector b3 = b1.cross (b2);                  // The cross product of orthogonal unit vectors is a unit vector.
    double l  = this.orth (b1).length ();

    return this.proj (b1).addScaled (b2, Math.sin (angle) * l).addScaled (b3, Math.cos (angle) * l);
  }
}
