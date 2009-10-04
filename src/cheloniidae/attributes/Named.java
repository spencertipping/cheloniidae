package cheloniidae.attributes;

import cheloniidae.Attribute;
import cheloniidae.Predicate;

import cheloniidae.predicates.CastableTo;

public class Named extends AtomicAttribute {
  public final String name;
  public Named (final String _name) {name = _name;}
  
  public Predicate<Attribute> projectivePredicate () {
    return new CastableTo<Named> (Named.class, new AtomicPredicate<Attribute> () {
      public boolean matches (final Named value) {return value.name.equals (name);}
    });
  }
}
