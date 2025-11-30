package org.dismefront.data.location;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "IS1_LOCATION")
@Data
public class Location {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column private long x;
  @Column private double y;

  @Column(nullable = false)
  private Float z; // Поле не может быть null
}
