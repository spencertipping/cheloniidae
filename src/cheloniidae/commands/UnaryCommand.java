package cheloniidae.commands;

public abstract class UnaryCommand<T> extends AtomicCommand {
  public final T value;
  public UnaryCommand (T _value) {value = _value;}
}
