package org.dismefront.data.product;

import jakarta.persistence.*;
import org.dismefront.data.coordinates.Coordinates;
import org.dismefront.data.organization.Organization;
import org.dismefront.data.person.Person;
import org.dismefront.data.shared.UnitOfMeasure;

import java.time.ZonedDateTime;

@Entity
@Table(name = "IS1_PRODUCT")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name; // Поле не может быть null, Строка не может быть пустой

  @JoinColumn(nullable = false)
  @OneToOne(cascade = CascadeType.ALL)
  private Coordinates coordinates; // Поле не может быть null

  @Column(nullable = false)
  private java.time.ZonedDateTime creationDate; // Поле не может быть null, Значение этого поля должно генерироваться автоматически

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UnitOfMeasure unitOfMeasure; // Поле не может быть null

  @JoinColumn(name = "manufacturer_id")
  @OneToOne(cascade = CascadeType.ALL)
  private Organization manufacturer; // Поле не может быть null

  @Column private long price; // Значение поля должно быть больше 0
  @Column private float manufactureCost;
  @Column private int rating; // Значение поля должно быть больше 0

  @Column
  private String partNumber; // Длина строки не должна быть больше 49, Поле не может быть null

  @JoinColumn(name = "owner_id")
  @OneToOne(cascade = CascadeType.ALL)
  private Person owner; // Поле может быть null

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Coordinates getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(Coordinates coordinates) {
    this.coordinates = coordinates;
  }

  public ZonedDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(ZonedDateTime creationDate) {
    this.creationDate = creationDate;
  }

  public UnitOfMeasure getUnitOfMeasure() {
    return unitOfMeasure;
  }

  public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
    this.unitOfMeasure = unitOfMeasure;
  }

  public Organization getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(Organization manufacturer) {
    this.manufacturer = manufacturer;
  }

  public long getPrice() {
    return price;
  }

  public void setPrice(long price) {
    this.price = price;
  }

  public float getManufactureCost() {
    return manufactureCost;
  }

  public void setManufactureCost(float manufactureCost) {
    this.manufactureCost = manufactureCost;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public String getPartNumber() {
    return partNumber;
  }

  public void setPartNumber(String partNumber) {
    this.partNumber = partNumber;
  }

  public Person getOwner() {
    return owner;
  }

  public void setOwner(Person owner) {
    this.owner = owner;
  }
}
