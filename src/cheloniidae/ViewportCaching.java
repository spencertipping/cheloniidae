package cheloniidae;

public abstract class ViewportCaching {
  private Viewport cachedViewport   = null;
  private long     cachedLastChange = 0;
  private double   cachedDepth      = 0.0;

  public abstract double computeDepth (Viewport v);

  public double depth (final Viewport v) {
    final double result = (cachedViewport == v && v.lastChange () == cachedLastChange) ?
                          cachedDepth : (cachedDepth = computeDepth (v));
    cachedViewport   = v;
    cachedLastChange = v.lastChange ();
    return result;
  }
}
