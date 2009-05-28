// This file was imported from the Rockhopper raytracer project.
// Point3D by Spencer Tipping, copyright 2006 (all rights reserved)
// Written some time ago; licensed under the LGPL, latest version

package cheloniidae;

import java.io.Serializable;

/**
 * This class implements a point or vector in three-dimensional space.
 * It provides the usual set of operators, such as addition, scaled addition,
 * multiplication by a factor, dot product, and cross product (there may
 * be more), but does not provide more advanced operators such as rotation.
 *
 * @author Spencer Tipping
 */

public final class Point3D implements Serializable {
  public double x = 0.0;
  public double y = 0.0;
  public double z = 0.0;

  public Point3D ()                             {}
  public Point3D (Point3D existing)             {this (existing.x, existing.y, existing.z);}
  public Point3D (double all)                   {this (all, all, all);}
  public Point3D (double x, double y, double z) {this.assign (x, y, z);}

  public String toString () {
    return "Point3D: <" + Double.toString (x) + ", " + Double.toString (y) + ", " + Double.toString (z) + ">";
  }

  public Point3D assign (Point3D value)                   {return this.assign (value.x, value.y, value.z);}
  public Point3D assign (double nx, double ny, double nz) {x = nx; y = ny; z = nz; return this;}
  public Point3D center ()                                {return this.assign (0, 0, 0);}


  public Point3D addScaled (Point3D other, double factor) {x += other.x * factor;
                                                           y += other.y * factor;
                                                           z += other.z * factor;
                                                           return this;}

  public Point3D add      (Point3D other) {x += other.x; y += other.y; z += other.z; return this;}
  public Point3D subtract (Point3D other) {x -= other.x; y -= other.y; z -= other.z; return this;}
  public Point3D multiply (Point3D other) {x *= other.x; y *= other.y; z *= other.z; return this;}
  public Point3D multiply (double factor) {x *= factor;  y *= factor;  z *= factor;  return this;}
  public Point3D divide   (Point3D other) {x /= other.x; y /= other.y; z /= other.z; return this;}
  public Point3D divide   (double factor) {x /= factor;  y /= factor;  z /= factor;  return this;}

  public double dot (Point3D other) {return x * other.x + y * other.y + z * other.z;}
  public double length ()           {return Math.sqrt (x*x + y*y + z*z);}
  public double lengthSquared ()    {return x*x + y*y + z*z;}

  public double distanceFrom (Point3D other) {return Math.sqrt ((x - other.x) * (x - other.x) +
                                                                (y - other.y) * (y - other.y) +
                                                                (z - other.z) * (z - other.z));}

  public Point3D cross (Point3D other) {return new Point3D (y * other.z - z * other.y,
                                                            z * other.x - x * other.z,
                                                            x * other.y - y * other.x);}
}
