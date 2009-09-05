package cheloniidae.proxyfunctions;

import cheloniidae.Vector;
import cheloniidae.commands.Proxy;

public class VectorSumProxy implements Proxy<Vector> {
  public final Proxy<Vector>[] components;
  public VectorSumProxy (Proxy<Vector> ... _components) {components = _components;}

  public Vector evaluate () {
    Vector result = new Vector ();
    for (Proxy<Vector> p : components) result.add (p.evaluate ());
    return result;
  }
}
