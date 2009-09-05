package cheloniidae.proxyfunctions;

import cheloniidae.commands.Proxy;

public class ScalarProductProxy implements Proxy<Double> {
  public final Proxy<Double>[] components;
  public ScalarProductProxy (Proxy<Double> ... _components) {components = _components;}

  public Double evaluate () {
    double result = 1.0;
    for (Proxy<Double> p : components) result *= p.evaluate ();
    return result;
  }
}
