import cheloniidae.*;

public class square {
  public static void main (String[] args) {
    TurtleDrawingWindow w = new TurtleDrawingWindow ();
    Turtle t = new Turtle ();
    w.add (t);
    w.setVisible (true);

    t.move (100);
    t.turn (90);
    t.move (100);
    t.turn (90);

    t.move (60);
    t.turn (90);
    t.move (60);
    t.turn (90);
    t.move (20);
    t.turn (90);
    t.move (33);

    t.turn (90);
    t.jump (5);
    t.move (1);
    t.jump (-6);
    t.turn (-90);
    t.move (27);

    t.turn (90);
    t.move (60);
    t.turn (90);

    t.move (100);
    t.turn (45);

    t.move (-10);
    t.move (40);

    t.turn (-45);
    t.move (20);
    t.turn (90);
    t.move (10);
    t.turn (90);
    t.move (10);

    t.turn (-90 + -45);
    t.move (26);
    t.turn (90);
    t.move (80);
    t.turnPhi (90);

    t.move (200);
    t.turnPhi (-90);
    t.turn (180);
    t.move (80);
    t.turn (-90);
    t.move (80);
    t.turnPhi (-90);
    t.move (200);
    t.jump (-200);
    t.turnPhi (90);

  }
}
