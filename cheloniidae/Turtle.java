// vim: set ts=4 sw=4 foldmethod=marker foldminlines=1 foldlevel=0 :					// {{{

//
// Cheloniidae Turtle System copyright 2006 by Spencer Tipping (all rights reserved)
// Written 07-26-2006; licensed under LGPL, latest version
//

//
// Package definition
// 

	package cheloniidae;


//
// Imports
// 
	
	import java.awt.*;
	import java.util.*;

																						// }}}

//
// Classes
//

	/**
	 * The Turtle class provides an object with a heading, location, color, and
	 * other attributes to facilitate easy turtle graphics drawing. To use one
	 * in code, see the example in {@see cheloniidae.TurtleDrawingWindow TurtleDrawingWindow}.
	 *
	 * @author spencer
	 */
	
		public class Turtle {															// {{{
			//
			// Constants
			//
																						// {{{
			
				public static final int				NO_DEFAULT_WINDOW	= 0;
					//
					// This constant is provided for compatibility with the Galapagos turtle
					// library. It has no use in Cheloniidae.
					//

				public static final int				Z_SPHERICAL			= 0;
					//
					// The Z spherical axis model means that phi changes the turtle's movement
					// base along the rho-theta plane into a cone with central angle 90 - phi.
					// 
					
				public static final int				Y_CYLINDRICAL		= 1;
					//
					// The Y cylindrical axis model means that phi rotates the turtle's plane
					// of movement (x, y) into Z-space.
					//

				public static final int				ORTHOGONAL_PLANAR	= 2;
					//
					// Uses the third angular measure. Phi is used as a base angle around the
					// Z axis. Then, xi is used as an elevation angle. After that, theta is
					// the angle within that plane.
					//
																						// }}}
			
			//
			// Fields
			// 
																						// {{{
				protected double					x					= 0.0;
				protected double					y					= 0.0;
				protected double					z					= TurtleDrawingWindow.DEFAULT_Z_BASE;
					//
					// These variables store the location of the turtle in 3D space.
					//

				protected double					headingTheta		= 0.0;
					//
					// This variable stores the heading of the turtle in degrees. It is never
					// corrected modulo 360, so it may contain values such as -180.0 or 540.0.
					//
					
				protected double					headingPhi			= 0.0;
					//
					// This variable stores the heading of the turtle on the r-z plane.
					//

				protected double					headingXi			= 0.0;
					//
					// This is used for orthogonal-planar coordinates. Xi is the elevatory
					// skew of the cylinder.
					//
				
				protected Color						bodyColor			= Color.BLUE;
					//
					// This is the color of the turtle as displayed on the screen.
					//

				protected Color						penColor			= Color.BLACK;
					//
					// This is the color that the turtle will draw. By default, it is set to
					// black, contrasting with the TurtleDrawingWindow's default background
					// of white.
					//

				protected boolean					penIsDown			= true;
					//
					// If the pen is down, then the turtle will draw when it moves. Otherwise,
					// the turtle will not leave any marks.
					//

				protected double					penSize				= 2.0;
					//
					// This specifies line width when the turtle is drawing.
					//

				protected java.util.List			lstLines			= null;
					//
					// This is the list of lines to which to add when the turtle moves.
					// It is set by the TurtleDrawingWindow and is not visible to the user.
					//

				protected TurtleDrawingWindow		window				= null;
					//
					// This is the window into which to draw. It receives a notification
					// when we make a new line by a call to drawLines (false). (The false
					// means that we don't redraw all lines, but just new ones)
					// 

				protected int						delayPerMove		= 500;
					//
					// This is the amount of time slept per move or turn command.
					//

				protected boolean					visible				= true;
					//
					// If true, the turtle is shown in the TurtleDrawingWindow. Otherwise,
					// the turtle is not shown.
					//

				protected int						polarAxisModel		= Z_SPHERICAL;
					//
					// This variable controls how the turtle handles phi and theta in 3D space.
					//

				protected Stack						stkTurtleStates		= new Stack ();
					//
					// This is used to store turtle location/angle information.
					//
																						// }}}

			//
			// Constructors
			//
																						// {{{
				public Turtle () {														// {{{
					//
					// Nothing needs to be done here.
					//
				}																		// }}}
				
				public Turtle (double _x, double _y) {									// {{{
					x = _x;
					y = _y;
				}																		// }}}
				
				public Turtle (double _x, double _y, double _headingTheta) {			// {{{
					this (_x, _y);
					headingTheta = _headingTheta;
				}																		// }}}

				public Turtle (int _graphicmode) {										// {{{
					// This is a dummy constructor to accomodate the default
					// Galapagos turtle constructor.
				}																		// }}}
																						// }}}

			//
			// Accessors
			//
																						// {{{
				public double	getX ()											{return x;}
				public void		setX (double _x)								{x = _x;}
				public double	getY ()											{return y;}
				public void		setY (double _y)								{y = _y;}
				public double	getZ ()											{return z;}
				public void		setZ (double _z)								{z = _z;}
				
				public double	getHeadingTheta ()								{return headingTheta;}
				public void		setHeadingTheta (double _headingTheta)			{headingTheta = _headingTheta;}
				public double	getHeadingPhi ()								{return headingPhi;}
				public void		setHeadingPhi (double _headingPhi)				{headingPhi = _headingPhi;}
				public double	getHeadingXi ()									{return headingXi;}
				public void		setHeadingXi (double _headingXi)				{headingXi = _headingXi;}

				public void		heading (double _headingTheta)					{setHeadingTheta (_headingTheta);}
					//
					// Note: The heading (double) method is supplied only for compatibility
					// 		 with the Galapagos turtle library.
					//

				public Color	getBodyColor ()									{return bodyColor;}
				public void		setBodyColor (Color _bodyColor)					{bodyColor = _bodyColor;}
				public void		bodyColor (Color _c)							{setBodyColor (_c);}
					//
					// Note: The bodyColor (Color) method is supplied only for compatibility
					// 		 with the Galapagos turtle library.
					//

				public Color	getPenColor () 									{return penColor;}
				public void		setPenColor (Color _penColor)					{penColor = _penColor;}
				public void		penColor (Color _penColor)						{setPenColor (_penColor);}
					//
					// Note: The penColor (Color) method is supplied only for compatibility
					// 		 with the Galapagos turtle library.
					//

				public boolean	getPenIsDown ()									{return penIsDown;}
				public void		setPenIsDown (boolean _penIsDown)				{penIsDown = _penIsDown;}
				public void		penDown ()										{setPenIsDown (true);}
				public void		penUp ()										{setPenIsDown (false);}
					//
					// Note: The penDown () and penUp () methods are supplied only for compatibility
					// 		 with the Galapagos turtle library.
					//

				public double	getPenSize ()									{return penSize;}
				public void		setPenSize (double _penSize)					{penSize = _penSize;}
				public void		penSize (double _penSize)						{setPenSize (_penSize);}
					//
					// Note: The penSize (double) method is supplied only for compatibility
					// 		 with the Galapagos turtle library.
					//

				public void		speed (int s)									{setDelayPerMove (1000 - s);}
					//
					// Note: The speed (int) method is supplied only for compatibility
					// 		 with the Galapagos turtle library.
					//

				public int		getDelayPerMove ()								{return delayPerMove;}
				public void		setDelayPerMove (int _delayPerMove)				{delayPerMove = _delayPerMove;}

				public boolean	getVisible ()									{return visible;}
				public void		setVisible (boolean _visible)					{visible = _visible;}

				void			setLines (java.util.List _lines)				{lstLines = _lines;}
				void			setWindow (TurtleDrawingWindow _window)			{window = _window;}
					//
					// These accessors are used by the TurtleDrawingWindow during the
					// TurtleDrawingWindow.add (Turtle) method.
					//

				public int		getPolarAxisModel ()							{return polarAxisModel;}
				public void		setPolarAxisModel (int _polarAxisModel)			{polarAxisModel = _polarAxisModel;}
																						// }}}
				
			//
			// Methods
			//
																						// {{{
				//
				// Turtle management (hidden from user)
				//
																						// {{{
					protected void line (												// {{{
							double x1, double y1, double z1,
							double x2, double y2, double z2) {
						//
						// Step 1: Make sure that the pen is down. If it isn't, then 
						// 		   all we need to do is to put in a delay.
						//

						if (penIsDown) {
							//
							// Step 2: Make sure we have a window and a line list. If
							// 		   we don't, then print a warning.
							//
							
							if (lstLines != null && window != null) {
								//
								// Add the new line to the list and prompt the turtle drawing
								// window to refresh its display.
								//

								synchronized (lstLines) {
									lstLines.add (
										new Line (x1, y1, z1, x2, y2, z2, penSize, penColor));
								}

								window.updateExtents (x1, x2, y1, y2, z1, z2);
								window.enqueueGraphicsRefreshRequest (false, true);
							} else
								System.err.println ("Warning: Turtle does not have a graphics window!");
						}
					}																	// }}}

					protected void startMove () {										// {{{
						//
						// Create the delay per move here.
						//

						if (delayPerMove > 0 && window.isVisible ())
							try {
								Thread.sleep (delayPerMove);
							} catch (InterruptedException e) {}
					}																	// }}}
					
					protected void finishMove () {										// {{{
						//
						// This method should be called whenever a move or turn operation is
						// completed. We tell the window to refresh its graphics.
						//
					
						window.enqueueGraphicsRefreshRequest (false, true);
						Thread.yield ();
					}																	// }}}

																						// }}}
				
				//
				// Turtle management (available to user)
				//
																						// {{{
					public void pushTurtleState () {									// {{{
						stkTurtleStates.push (new double [] {x, y, z, headingTheta, headingPhi, headingXi});
					}																	// }}}

					public void popTurtleState () {										// {{{
						double [] state = (double []) stkTurtleStates.pop ();

						x = state [0];
						y = state [1];
						z = state [2];
						headingTheta = state [3];
						headingPhi = state [4];
						headingXi = state [5];

						finishMove ();
					}																	// }}}

					public Turtle replicate () {										// {{{
						//
						// Returns a duplicate of this turtle.
						//

						Turtle result = new Turtle (x, y);

						result.setZ (z);
						result.setHeadingTheta (headingTheta);
						result.setHeadingPhi (headingPhi);
						result.setHeadingXi (headingXi);
						result.setBodyColor (bodyColor);
						result.setPenColor (penColor);
						result.setPenIsDown (penIsDown);
						result.setPenSize (penSize);
						result.setDelayPerMove (delayPerMove);
						result.setVisible (visible);
						result.setPolarAxisModel (polarAxisModel);

						window.add (result);

						return result;
					}																	// }}}
																						// }}}
				
				//
				// Turtle information (available to user)
				//
																						// {{{
					public Point3D computeTurtleVector (double distance) {				// {{{
						//
						// Computes the difference in location of the turtle based on the
						// distance that it would travel.
						//

						Point3D result = new Point3D ();
						
						if (polarAxisModel == Z_SPHERICAL) {
							result.X =
								Math.cos (Math.toRadians (headingTheta)) *
								Math.cos (Math.toRadians (headingPhi)) * distance;
							
							result.Y =
								Math.sin (Math.toRadians (headingTheta)) *
								Math.cos (Math.toRadians (headingPhi)) * distance;

							result.Z =
								Math.sin (Math.toRadians (headingPhi)) * distance;	
						} else if (polarAxisModel == Y_CYLINDRICAL) {
							result.X =
								Math.cos (Math.toRadians (headingTheta)) *
								Math.cos (Math.toRadians (headingPhi)) * distance;

							result.Y =
								Math.sin (Math.toRadians (headingTheta)) * distance;

							result.Z =
								Math.cos (Math.toRadians (headingTheta)) *
								Math.sin (Math.toRadians (headingPhi)) * distance;
						} else if (polarAxisModel == ORTHOGONAL_PLANAR) {
							result.X =
								(Math.cos (Math.toRadians (headingTheta)) *
								 Math.cos (Math.toRadians (headingPhi)) +
								 Math.sin (Math.toRadians (headingTheta)) *
								 Math.sin (Math.toRadians (headingPhi)) *
								 Math.sin (Math.toRadians (headingXi))) * distance;

							result.Y =
								Math.sin (Math.toRadians (headingTheta)) *
								Math.cos (Math.toRadians (headingXi)) * distance;

							result.Z =
								(Math.cos (Math.toRadians (headingTheta)) *
								 Math.sin (Math.toRadians (headingPhi)) -
								 Math.sin (Math.toRadians (headingTheta)) *
								 Math.cos (Math.toRadians (headingPhi)) *
								 Math.sin (Math.toRadians (headingXi))) * distance;
						} else
							System.err.println ("Warning: Current polar axis model is not supported.");

						return result;
					}																	// }}}
																						// }}}
					
				//
				// Turtle movement commands (available to user)
				//
																						// {{{
					public void move (double distance) {								// {{{
						move (distance, false);
					}																	// }}}
					
					public void jump (double distance) {								// {{{
						move (distance, true);
					}																	// }}}
					
					public void move (double distance, boolean invisible) {				// {{{
						//
						// Angles should go CCW from due east as theta varies.
						//

						startMove ();

						Point3D delta = computeTurtleVector (distance);

						if ((z + delta.Z) < 1.0)
							delta.Z = 1.0 - z;

						if (!invisible)
							line (x, y, z, x + delta.X, y + delta.Y, z + delta.Z);

						x += delta.X;
						y += delta.Y;
						z += delta.Z;
						
						finishMove ();
					}																	// }}}

					public void moveTo (double nx, double ny) {							// {{{
						startMove ();
						line (x, y, z, nx, ny, z);
						x = nx;
						y = ny;
						finishMove ();
					}																	// }}}

					public void moveTo (double nx, double ny, double nz) {				// {{{
						startMove ();
						line (x, y, z, nx, ny, nz);
						x = nx;
						y = ny;
						z = nz;
						finishMove ();
					}																	// }}}

					public void jumpTo (double nx, double ny) {							// {{{
						startMove ();
						x = nx;
						y = ny;
						finishMove ();
					}																	// }}}

					public void jumpTo (double nx, double ny, double nz) {				// {{{
						startMove ();
						x = nx;
						y = ny;
						z = nz;
						finishMove ();
					}																	// }}}

					public void forward (double distance) {								// {{{
						move (distance);
					}																	// }}}

					public void backup (double distance) {								// {{{
						move (-distance);
					}																	// }}}

					public void turnTheta (double angle) {								// {{{
						startMove ();
						headingTheta += angle;
						finishMove ();
					}																	// }}}

					public void turn (double angle) {									// {{{
						turnTheta (angle);
					}																	// }}}

					public void turnPhi (double angle) {								// {{{
						startMove ();
						headingPhi += angle;
						finishMove ();
					}																	// }}}

					public void turnXi (double angle) {									// {{{
						startMove ();
						headingXi += angle;
						finishMove ();
					}																	// }}}
																						// }}}
 																						// }}}
		}																				// }}}
