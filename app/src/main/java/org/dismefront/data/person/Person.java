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
  private String name;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Color eyeColor;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Color hairColor;

  @JoinColumn(
      name = "location_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_person_location"))
  @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  private Location location;

  @Column private Long height;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Country nationality;
}
