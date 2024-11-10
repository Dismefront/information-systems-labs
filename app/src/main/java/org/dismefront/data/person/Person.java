package org.dismefront.data.person;

import jakarta.persistence.*;
import org.dismefront.data.location.Location;
import org.dismefront.data.shared.Color;
import org.dismefront.data.shared.Country;

@Entity
@Table(name = "IS1_PERSON")
public class Person {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name; // Поле не может быть null, Строка не может быть пустой

  @Column(nullable = false, name = "eye_color")
  @Enumerated(EnumType.STRING)
  private Color eyeColor; // Поле не может быть null

  @Column(nullable = false, name = "hair_color")
  @Enumerated(EnumType.STRING)
  private Color hairColor; // Поле может быть null

  @JoinColumn(nullable = false)
  @OneToOne(cascade = CascadeType.ALL)
  private Location location; // Поле не может быть null

  @Column(nullable = false)
  private Long height; // Поле может быть null, Значение поля должно быть больше 0

  @Column(nullable = false, name = "nationality")
  @Enumerated(EnumType.STRING)
  private Country nationality; // Поле не может быть null

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

  public Color getEyeColor() {
    return eyeColor;
  }

  public void setEyeColor(Color eyeColor) {
    this.eyeColor = eyeColor;
  }

  public Color getHairColor() {
    return hairColor;
  }

  public void setHairColor(Color hairColor) {
    this.hairColor = hairColor;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public Long getHeight() {
    return height;
  }

  public void setHeight(Long height) {
    this.height = height;
  }

  public Country getNationality() {
    return nationality;
  }

  public void setNationality(Country nationality) {
    this.nationality = nationality;
  }
}
