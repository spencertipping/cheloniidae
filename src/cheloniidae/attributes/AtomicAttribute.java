package cheloniidae.attributes;

import cheloniidae.Attribute;
import cheloniidae.Transformation;

public abstract class AtomicAttribute implements Attribute {
  public Attribute map (final Transformation<Attribute> t) {return t.transform (this);}
  public int compareTo (final Attribute rhs) {return new Integer (this.hashCode ()).compareTo (rhs.hashCode ());}
}
