package cheloniidae.proxyfunctions;

import cheloniidae.Vector;
import cheloniidae.commands.Proxy;

public class VectorProductProxy implements Proxy<Vector> {
  public final Proxy<Vector>[] components;
  public VectorProductProxy (Proxy<Vector> ... _components) {components = _components;}

  public Vector evaluate () {
    Vector result = new Vector ();
    for (Proxy<Vector> p : components) result.multiply (p.evaluate ());
    return result;
  }
}
