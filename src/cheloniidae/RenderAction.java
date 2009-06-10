package cheloniidae;

import java.awt.Graphics2D;

public interface RenderAction extends HasPerspectiveProjection {
  public void render (Viewport viewport);
}
