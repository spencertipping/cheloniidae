package cheloniidae;

public interface SupportsLineSize<T implements Turtle> {
  public double lineSize ();
  public T      lineSize (double size);
}
