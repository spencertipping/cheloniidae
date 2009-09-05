package cheloniidae.proxyfunctions;

import cheloniidae.commands.Proxy;

public class ScalarSumProxy implements Proxy<Double> {
  public final Proxy<Double>[] components;
  public ScalarSumProxy (Proxy<Double> ... _components) {components = _components;}

  public Double evaluate () {
    double result = 0.0;
    for (Proxy<Double> p : components) result += p.evaluate ();
    return result;
  }
}
