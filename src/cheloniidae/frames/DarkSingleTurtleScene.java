package cheloniidae.frames;

import java.awt.Color;

public abstract class DarkSingleTurtleScene extends SingleTurtleScene {
  public DarkSingleTurtleScene initialize () {
    window.setBackground (new Color (0.05f, 0.06f, 0.08f));
    turtle.color (new Color (0.8f, 0.8f, 0.9f, 0.5f));
    super.initialize ();
    return this;
  }
}
