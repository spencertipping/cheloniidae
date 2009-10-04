package cheloniidae.attributes;

import cheloniidae.Attribute;
import cheloniidae.Predicate;

import cheloniidae.predicates.CastableTo;
import cheloniidae.predicates.AtomicPredicate;

public class Named extends AtomicAttribute {
  public final String name;
  public Named (final String _name) {name = _name;}
  
  public Predicate<Attribute> projectivePredicate () {
    return new CastableTo<Attribute, Named> (Named.class, new AtomicPredicate<Named> () {
      public boolean matches (final Named value) {return value.name.equals (name);}
    });
  }
}
