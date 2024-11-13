package org.dismefront.data.person;

import jakarta.persistence.*;
import lombok.Data;
import org.dismefront.data.location.Location;
import org.dismefront.data.shared.Color;
import org.dismefront.data.shared.Country;

@Entity
@Table(name = "IS1_PERSON")
@Data
public class Person {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name; // Поле не может быть null, Строка не может быть пустой

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Color eyeColor; // Поле не может быть null

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Color hairColor; // Поле может быть null

  @JoinColumn(nullable = false)
  @OneToOne(cascade = CascadeType.ALL)
  private Location location; // Поле не может быть null

  @Column(nullable = false)
  private Long height; // Поле может быть null, Значение поля должно быть больше 0

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Country nationality; // Поле не может быть null
}
