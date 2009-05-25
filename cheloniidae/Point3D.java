// vim: set ts=4 sw=4 foldmethod=marker foldminlines=1 foldlevel=0 :					// {{{

//
// This file was imported from the Rockhopper raytracer project.
//
// Point3D by Spencer Tipping, copyright 2006 (all rights reserved)
// Written some time ago; licensed under the LGPL, latest version
//

//
// Package definition
//

	package cheloniidae;


//
// Imports
//

	import java.io.Serializable;

																						// }}}
	
//
// Classes
//

	/**
	 * This class implements a point or vector in three-dimensional space.
	 * It provides the usual set of operators, such as addition, scaled addition,
	 * multiplication by a factor, dot product, and cross product (there may
	 * be more), but does not provide more advanced operators such as rotation.
	 *
	 * @author Spencer Tipping
	 */
	
		public class Point3D implements Serializable {									// {{{
			//
			// Fields
			//
																						// {{{	
				public double		X		= 0.0;
				public double		Y		= 0.0;
				public double		Z		= 0.0;
																						// }}}

			//
			// Constructors
			//
																						// {{{
				public Point3D () {}
				
				public Point3D (double x, double y, double z) {							// {{{
					X = x;
					Y = y;
					Z = z;
				}																		// }}}

				public Point3D (Point3D existing) {										// {{{
					X = existing.X;
					Y = existing.Y;
					Z = existing.Z;
				}																		// }}}

				public Point3D (double all) {											// {{{
					X = Y = Z = all;
				}																		// }}}
																						// }}}

			//
			// Methods
			//
																						// {{{
				public String toString () {												// {{{
					return "Point3D: <" + Double.toString (X) + ", " + Double.toString (Y) + ", " + Double.toString (Z) + ">";
				}																		// }}}
	
				public void assign (Point3D value) {									// {{{
					X = value.X;
					Y = value.Y;
					Z = value.Z;
				}																		// }}}

				public void assign (double x, double y, double z) {						// {{{
					X = x;
					Y = y;
					Z = z;
				}																		// }}}

				public void center () {													// {{{
					X = Y = Z = 0.0;
				}																		// }}}
				
				public void add (Point3D other) {										// {{{
					X += other.X;
					Y += other.Y;
					Z += other.Z;
				}																		// }}}

				public void addScaled (Point3D other, double factor) {					// {{{
					X += other.X * factor;
					Y += other.Y * factor;
					Z += other.Z * factor;
				}																		// }}}

				public void subtract (Point3D other) {									// {{{
					X -= other.X;
					Y -= other.Y;
					Z -= other.Z;
				}																		// }}}

				public void multiply (Point3D other) {									// {{{
					X *= other.X;
					Y *= other.Y;
					Z *= other.Z;
				}																		// }}}

				public void multiply (double factor) {									// {{{
					X *= factor;
					Y *= factor;
					Z *= factor;
				}																		// }}}

				public void divide (Point3D other) {									// {{{
					X /= other.X;
					Y /= other.Y;
					Z /= other.Z;
				}																		// }}}

				public void divide (double factor) {									// {{{
					X /= factor;
					Y /= factor;
					Z /= factor;
				}																		// }}}

				public double dot (Point3D other) {										// {{{
					return X * other.X + Y * other.Y + Z * other.Z;
				}																		// }}}

				public Point3D cross (Point3D other) {									// {{{
					return new Point3D (
						Y * other.Z - Z * other.Y,
						Z * other.X - X * other.Z,
						X * other.Y - Y * other.X);
				}																		// }}}
				
				public double length () {												// {{{
					return Math.sqrt (X*X + Y*Y + Z*Z);
				}																		// }}}

				public double lengthSquared () {										// {{{
					return X*X + Y*Y + Z*Z;
				}																		// }}}

				public double distanceFrom (Point3D other) {							// {{{
					return Math.sqrt ((X - other.X) * (X - other.X) +
							  (Y - other.Y) * (Y - other.Y) +
							  (Z - other.Z) * (Z - other.Z));
				}																		// }}}
																						// }}}
		}																				// }}}
