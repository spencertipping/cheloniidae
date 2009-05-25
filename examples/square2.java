import cheloniidae.*;

public class square2 {
  public static void main (String[] args) {
    TurtleDrawingWindow w = new TurtleDrawingWindow ();
    Turtle t = new Turtle ();
    w.add (t);
    w.setVisible (true);

    t.move(150);
    t.turn(60);
    t.move(150);
    t.turn(60);      
    t.move(150);
    t.turn(60);
    t.move(150);
    t.turn(60);
    t.move(150);
    t.turn(60);
    t.move(150);
  }
}
