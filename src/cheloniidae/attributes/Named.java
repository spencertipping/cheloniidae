package cheloniidae.attributes;

import cheloniidae.Attribute;
import cheloniidae.Predicate;

public class Named extends AtomicAttribute {
  public final String name;
  public Named (final String _name) {name = _name;}
  
  public Predicate<Attribute> projectivePredicate () {
    
  }
}
