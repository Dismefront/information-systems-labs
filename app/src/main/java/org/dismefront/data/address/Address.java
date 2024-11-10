package org.dismefront.data.address;

import jakarta.persistence.*;
import org.dismefront.data.location.Location;

@Entity
@Table(name = "IS1_ADDRESS")
public class Address {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 13)
  private String zipCode; // Длина строки не должна быть больше 13, Поле не может быть null

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public Location getTown() {
    return town;
  }

  public void setTown(Location town) {
    this.town = town;
  }

  @Column(nullable = true)
  private Location town; // Поле может быть null
}
