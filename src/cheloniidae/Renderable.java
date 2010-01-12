package cheloniidae;
import java.util.SortedSet;
public interface Renderable {
  public SortedSet<RenderAction> actions (Viewport v);
}
