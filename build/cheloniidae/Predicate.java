package cheloniidae;
public interface Predicate<T> extends Transformable<Predicate<T>> {
  public boolean matches (T value);
}
