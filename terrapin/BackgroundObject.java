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

	import java.util.*;
																						// }}}

//
// Interfaces
//

	public interface BackgroundObject {													// {{{
		//
		// Methods
		//
																						// {{{
			public LinkedList getLines ();
				//
				// This method should return the list of lines that make up
				// the object in question. The LinkedList should be populated
				// with Line objects.
				//
																						// }}}
		
	}																					// }}}
