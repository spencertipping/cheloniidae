package cheloniidae;

import java.awt.Graphics2D;

public interface RenderAction {
  public double distance ();
  public void   render   (Viewport viewport);
}
