// Terrapin Turtle System copyright 2006 by Spencer Tipping
// Written 07-26-2006; licensed under the LGPL, latest version

  package terrapin;

  import java.awt.*;
  import java.awt.event.*;
  
/**
 * This class implements a window into which turtles can draw. To use it, try
 * this code:
 *
 * <code>
 * Turtle t = new Turtle ();
 * TurtleDrawingWindow w = new TurtleDrawingWindow ();
 * w.add (t);
 * w.setVisible (true);
 * </code>
 *
 * @author Spencer Tipping
 */

  public class TurtleDrawingWindow extends Frame {
    // Fields
      protected TurtleCanvas canvas = new TurtleCanvas ();

    // Constructors
      public TurtleDrawingWindow () {
        super ("Terrapin");
        super.setSize (600, 350);
        super.setLayout (new BorderLayout ());
        super.add (canvas, BorderLayout.CENTER);

        final TurtleDrawingWindow _this = this;

        super.addWindowListener (new WindowListener () {
          public void windowClosing (WindowEvent e) {
            _this.dispose ();
          }

          public void windowOpened      (WindowEvent e) {}
          public void windowClosed      (WindowEvent e) {}
          public void windowIconified   (WindowEvent e) {}
          public void windowDeiconified (WindowEvent e) {}
          public void windowActivated   (WindowEvent e) {}
          public void windowDeactivated (WindowEvent e) {}
        });
      }

    // Accessors
      public TurtleCanvas getCanvas ()                  {return canvas;}
      public void setCanvas (TurtleCanvas _canvas)      {super.remove (canvas); super.add (canvas = _canvas, BorderLayout.CENTER);}

    // Methods
      public void add (TurtleBase t) {
        // This gets passed straight through to the canvas.
        canvas.add (t);
      }

      public void add (BackgroundObject b) {
        canvas.add (b);
      }
  }
