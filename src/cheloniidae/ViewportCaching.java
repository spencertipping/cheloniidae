package cheloniidae;

public abstract class ViewportCaching {
  private Viewport cachedViewport = null;
  private double   cachedDepth    = 0.0;

  public abstract double computeDepth (Viewport v);

  public double depth (Viewport v) {
    final double result = (cachedViewport == v || cachedViewport.equals (v)) ? cachedDepth : (cachedDepth = computeDepth (v));
    cachedViewport = v;
    return result;
  }
}
