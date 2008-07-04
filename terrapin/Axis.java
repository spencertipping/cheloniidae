// vim: set ts=4 sw=4 foldmethod=marker foldminlines=1 foldlevel=0 :					// {{{

//
// Terrapin Turtle System copyright 2006 by Spencer Tipping (all rights reserved)
// Written 08-02-2006; licensed under the LGPL, latest version
//

//
// Package definition
//

	package terrapin;


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
	 * Add this class to a TurtleDrawingWindow to create an axis or axes.
	 *
	 * @author Spencer Tipping
	 */

		public class Axis implements BackgroundObject {									// {{{
			// 
			// Constants
			//
																						// {{{
				public static final int	X_AXIS					= 1 << 0;
				public static final int	Y_AXIS					= 1 << 1;
				public static final int	Z_AXIS					= 1 << 2;
					//
					// Set any one of these bits to have the corresponding
					// axis on the screen.
					//
																						// }}}
			
			//
			// Fields
			//
																						// {{{
				protected int			axes					= Z_AXIS;
					//
					// This variable controls which axes are displayed on the screen.
					// It is a bitmask of the axial constants.
					//
				
				protected double		axisLength				= 400.0;
					//
					// These variables control how large and dense the axis is.
					//

				protected double		tickSpacing				= 50.0;
					// 
					// This variable controls how far apart the ticks are.
					//

				protected Color			axisColor				= new Color (0.3f, 0.4f, 0.6f, 0.4f);
					//
					// This variable specifies what color the axis is.
					// 

				protected double		lineThickness			= 0.5;
					//
					// This variable specifies the thickness of the lines.
					//

				protected double		tickSize				= 2.0;
					// 
					// This variable specifies the length of the tick marks' crosshairs.
					//

				protected LinkedList	cache					= null;
					//
					// This variable prevents us from having to reconstruct the list
					// during each screen refresh.
					//
																						// }}}

			//
			// Constructors
			//
																						// {{{
				public Axis () {														// {{{
					//
					// Do nothing.
					//
				}																		// }}}

				public Axis (int _axes) {												// {{{
					axes = _axes;
				}																		// }}}
																						// }}}

			//
			// Accessors
			//
																						// {{{
				public int		getAxes ()									{return axes;}
				public void		setAxes (int _axes)							{axes = _axes;}

				public double	getAxisLength ()							{return axisLength;}
				public void		setAxisLength (double _axisLength)			{axisLength = _axisLength;}

				public double	getTickSpacing ()							{return tickSpacing;}
				public void		setTickSpacing (double _tickSpacing)		{tickSpacing = _tickSpacing;}

				public Color	getAxisColor ()								{return axisColor;}
				public void		setAxisColor (Color _axisColor)				{axisColor = _axisColor;}
				
				public double	getLineThickness ()							{return lineThickness;}
				public void		setLineThickness (double _lineThickness)	{lineThickness = _lineThickness;}

				public double	getTickSize ()								{return tickSize;}
				public void		setTickSize (double _tickSize)				{tickSize = _tickSize;}
																						// }}}
				
			//
			// Methods
			// 
																						// {{{
				//
				// Grid management (available to user)
				//
																						// {{{
					public void reinitializeGrid () {									// {{{
						//
						// This method should be called whenever the grid's parameters
						// are changed at runtime.
						//
						cache = null;
					}																	// }}}

					public LinkedList getLines () {										// {{{
						//
						// Draw large axis lines and then small cross lines for the
						// individual tick marks on the axes.
						//

						if (cache != null)
							return cache;
						else {
							int total			= (int) (axisLength / tickSpacing);
							LinkedList result	= new LinkedList ();

							if ((axes & X_AXIS) != 0)
								result.add (new Line (
									-axisLength, 0, TurtleDrawingWindow.DEFAULT_Z_BASE,
									axisLength, 0, TurtleDrawingWindow.DEFAULT_Z_BASE,
									lineThickness, axisColor));

							if ((axes & Y_AXIS) != 0)
								result.add (new Line (
									0, -axisLength, TurtleDrawingWindow.DEFAULT_Z_BASE,
									0, axisLength, TurtleDrawingWindow.DEFAULT_Z_BASE,
									lineThickness, axisColor));

							if ((axes & Z_AXIS) != 0)
								result.add (new Line (
									0, 0, -axisLength + TurtleDrawingWindow.DEFAULT_Z_BASE,
									0, 0, axisLength + TurtleDrawingWindow.DEFAULT_Z_BASE,
									lineThickness, axisColor));
							
							for (int i = -total; i <= total; i++) {
								if ((axes & X_AXIS) != 0)
									makeTick (i * tickSpacing, 0, TurtleDrawingWindow.DEFAULT_Z_BASE, result, X_AXIS);

								if ((axes & Y_AXIS) != 0)
									makeTick (0, i * tickSpacing, TurtleDrawingWindow.DEFAULT_Z_BASE, result, Y_AXIS);

								if ((axes & Z_AXIS) != 0)
									makeTick (0, 0, i * tickSpacing + TurtleDrawingWindow.DEFAULT_Z_BASE, result, Z_AXIS);
							}

							return cache = result;
						}
					}																	// }}}
																						// }}}
				//
				// Axis management (hidden from user)
				//
																						// {{{
					protected void makeTick (											// {{{
							double x, double y, double z, LinkedList target, int orientation) {
						//
						// Draw two line segments.
						// 

						if (orientation != X_AXIS)
							target.add (new Line (x + tickSize, y, z, x - tickSize, y, z, lineThickness, axisColor));

						if (orientation != Y_AXIS)
							target.add (new Line (x, y + tickSize, z, x, y - tickSize, z, lineThickness, axisColor));

						if (orientation != Z_AXIS)
							target.add (new Line (x, y, z + tickSize, x, y, z - tickSize, lineThickness, axisColor));
					}																	// }}}
																						// }}}
																						// }}}
		}																				// }}}
