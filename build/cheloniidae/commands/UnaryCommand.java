package cheloniidae.commands;
public abstract class UnaryCommand<T> extends AtomicCommand {
  public final T value;
  public UnaryCommand (final T _value) {value = _value;}
}
