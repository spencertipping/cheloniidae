import terrapin.*;
import java.awt.Color;
import java.util.Random;

public class grass {
  public static void main (String[] args) {
    TurtleDrawingWindow w = new TurtleDrawingWindow ();
    Turtle t = new Turtle ();
    w.add (t);
    t.turn (-90);
    t.jump (-100);
    grass (t, 20, 5000, 40, 0.5, 24.0, new Random ());
    trees (t, 60, 20, 14, 60, 4, new Random ());
    w.setVisible (true);
    System.out.println ("");
  }

  public static void trees (Turtle t, double distance, int trees, int recursionLevel,
      double treeHeight, double distanceBetweenTrees, Random r) {
    for (int i = 0; i < trees; i++) {
      Turtle immediate = t.replicate ();
      immediate.setPolarAxisModel (Turtle.Y_CYLINDRICAL);
      
      // Rotate the turtle to run parallel with the ground, and then rotate some
      // random amount in phi. Then move some random amount less than /distance/.
      immediate.turn (90);
      immediate.turnPhi (360.0 * r.nextDouble ());
      immediate.jump (distance * distanceBetweenTrees * (1.0 - r.nextDouble () * r.nextDouble ()));
      immediate.turn (-90);
      immediate.turnPhi (360.0 * r.nextDouble ());

      tree (immediate, treeHeight, recursionLevel, r);
      immediate.setVisible (false);

      System.out.print ("-");
    }
  }

  public static void grass (Turtle t, double distance, int clusters, int bladesPerCluster,
      double thickness, double distanceBetweenClusters, Random r) {
    // Create a bunch of turtle replicas, move them by various amounts, and then
    // draw. We assume that the original turtle is pointing straight up.
    t.setPolarAxisModel (Turtle.Y_CYLINDRICAL);

    for (int i = 0; i < clusters; i++) {
      Turtle immediate = t.replicate ();
      immediate.setPolarAxisModel (Turtle.Y_CYLINDRICAL);

      // Rotate the turtle to run parallel with the ground, and then rotate some
      // random amount in phi. Then move some random amount less than /distance/.
      immediate.turn (90);
      immediate.turnPhi (360.0 * r.nextDouble ());
      immediate.jump (distance * distanceBetweenClusters * (1.0 - r.nextDouble () * r.nextDouble ()));
      
      // Here's where we create the cluster.
      for (int j = 0; j < bladesPerCluster; j++) {
	immediate.setPenColor (new Color (0.0f, 0.4f * (0.5f + 0.5f * r.nextFloat ()), 0.1f * (0.2f + 0.8f * r.nextFloat ()), 0.5f));
	immediate.pushTurtleState ();
	immediate.setPenSize ((r.nextDouble () * 0.8 + 0.2) * thickness);
	immediate.turn (-60 - r.nextDouble () * 60.0);
	immediate.turnPhi (360.0 * r.nextDouble ());

	double immediate_distance = distance * (0.1 + 0.9 * r.nextDouble ());

	for (int k = 0; k < 5; k++) {
	  immediate.move (immediate_distance * (0.4 + 0.6 * r.nextDouble ()) * Math.pow (0.75, k));
	  immediate.turn (15.0 * (0.6 + 0.4 * r.nextDouble ()));
	  immediate.turnPhi (10.0 * (0.3 + 0.7 * r.nextDouble ()));
	  immediate.setPenSize (immediate.getPenSize () * 0.75);
	}

	immediate.popTurtleState ();
      }

      for (int j = 0; j < 20; j++) {
	immediate.setPenColor (new Color (0.3f, 0.25f, 0.2f, 0.9f));
	immediate.pushTurtleState ();
	immediate.turnPhi (360.0 * r.nextDouble ());
	immediate.setPenSize (thickness * 4.0 * (0.1 + 0.9 * r.nextDouble ()));
	immediate.move (distance * distanceBetweenClusters * 0.1);
	immediate.popTurtleState ();
      }

      immediate.setVisible (false);
      System.out.print ("+");
    }

    t.setPolarAxisModel (Turtle.Z_SPHERICAL);
  }

  public static void tree (Turtle t, double distance, int recursionLevel, Random r) {
    float branchBrightness = 0.8f + 0.2f * r.nextFloat ();
    t.setPenColor (new Color (0.3f * branchBrightness, 0.25f * branchBrightness, 0.2f * branchBrightness, 0.8f));
    t.setPenSize (distance * distance / 200.0);
    t.move (distance * (0.3 + 0.7 * r.nextDouble ()));
    t.turn (-15 * r.nextDouble () * 2.0);

    t.turnPhi ((r.nextDouble () - 0.5) * 60.0);

    if (recursionLevel > 0) {
      t.pushTurtleState ();
      tree (t, distance / 1.04 * (0.8 + 0.2 * r.nextDouble ()), recursionLevel - 1, r);
      t.popTurtleState ();
      t.turn (30 + r.nextDouble () * 4.0);
      t.pushTurtleState ();
      tree (t, distance / 1.04 * (0.8 + 0.2 * r.nextDouble ()), recursionLevel - 1, r);
      t.popTurtleState ();
    } else {
      t.jump (distance * -0.3);
      t.setPenSize (distance * distance / 300.0);

      float leafBrightness = 0.5f + 0.5f * r.nextFloat ();
      t.setPenColor (new Color (0.0f, 0.3f * leafBrightness, 0.0f, 0.5f));

      t.turnPhi ((r.nextDouble () - 0.5) * 360.0);

      for (int i = 0; i < 4; i++) {
        t.pushTurtleState ();
        t.turn (-45 + (r.nextDouble () - 0.5) * 15.0);
        t.move (distance * 0.2 * (4 - i));
        t.popTurtleState ();
        t.pushTurtleState ();
        t.turn (45 + (r.nextDouble () - 0.5) * 15.0);
        t.move (distance * 0.2 * (4 - i));
        t.popTurtleState ();
        t.jump (distance * 0.3);
      }
    }
  }
}
