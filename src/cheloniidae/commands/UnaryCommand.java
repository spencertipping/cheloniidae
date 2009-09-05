package cheloniidae.commands;

import cheloniidae.proxyfunctions.ConstantProxy;
import cheloniidae.TurtleCommand;

public abstract class UnaryCommand<T> implements TurtleCommand {
  public final Proxy<T> proxy;
  public UnaryCommand (final Proxy<T> _proxy) {proxy = _proxy;}
  public UnaryCommand (final T value) {
    this (new ConstantProxy<T> (value));
  }

  public T value () {
    return proxy.evaluate ();
  }
}
