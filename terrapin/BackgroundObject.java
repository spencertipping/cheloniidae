// Terrapin Turtle System copyright 2006 by Spencer Tipping
// Written 08-02-2006; licensed under the LGPL, latest version

  package terrapin;

  import java.util.*;

public interface BackgroundObject {
  // This method should return the list of lines that make up
  // the object in question. The LinkedList should be populated
  // with Line objects.
  public LinkedList getLines ();
}
