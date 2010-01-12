package cheloniidae;

import java.awt.Graphics2D;

public interface Viewport {
  public boolean    shouldCancel        ();
  public Graphics2D context             ();
  public double     scaleFactor         ();
  public Vector     transformPoint      (Vector v);
  public Vector     projectPoint        (Vector v);
  public Viewport   representativePoint (Vector v);
  public long       lastChange          ();
}
