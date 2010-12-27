package cheloniidae.attributes;
import cheloniidae.Attribute;
import cheloniidae.Transformation;
public abstract class AtomicAttribute implements Attribute {
  public Attribute map (final Transformation<Attribute> t) {return t.transform (this);}
}
