package org.dismefront.data.location;

import jakarta.persistence.*;

@Entity
@Table(name = "IS1_LOCATION")
public class Location {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column private long x;
  @Column private double y;

  @Column(nullable = false)
  private Float z; // Поле не может быть null

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public long getX() {
    return x;
  }

  public void setX(long x) {
    this.x = x;
  }

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
  }

  public Float getZ() {
    return z;
  }

  public void setZ(Float z) {
    this.z = z;
  }
}
