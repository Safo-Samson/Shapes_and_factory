package oop.cw2_2223.painton.shapes.configui;

import java.util.Objects;

public class PropertyKey implements Comparable<PropertyKey> {
  public enum PropertyType {COLOR, STRING, BOOLEAN, PROPORTION, FILE }

  private final String name;
  private final PropertyType type;
  
  public PropertyKey(final String name, final PropertyType type) {
    this.name = name;
    this.type = type;
  }

  public String getName() {
    return this.name;
  }

  public PropertyType getType() {
    return this.type;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.name, this.type);
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final PropertyKey other = (PropertyKey) obj;
    return Objects.equals(this.name, other.name) && (this.type == other.type);
  }

  @Override
  public int compareTo(final PropertyKey other) {
    return this.name.compareTo(other.name);
  }

  @Override
  public String toString() {
    return "PropertyKey [name=" + this.name + ", type=" + this.type + "]";
  }

}
