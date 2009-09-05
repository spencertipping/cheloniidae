package cheloniidae.proxyfunctions;

import cheloniidae.commands.Proxy;

public class ConstantProxy<T> implements Proxy<T> {
  public final T value;
  public ConstantProxy (T _value) {value = _value;}
  public T evaluate () {return value;}
}
