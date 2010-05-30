package cheloniidae.frames;

import cheloniidae.Turtle;
import static cheloniidae.frames.CoreCommands.color;

import java.awt.Color;

public abstract class DarkSingleTurtleScene<T extends Turtle> extends SingleTurtleScene<T> {
  public DarkSingleTurtleScene initialize () {
    window.setBackground (new Color (0.05f, 0.06f, 0.08f));
    turtle.run (color (new Color (0.8f, 0.8f, 0.9f, 0.3f)));
    super.initialize ();
    return this;
  }
}
