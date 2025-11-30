package org.dismefront.data.coordinates;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "IS1_COORDINATES")
@Data
public class Coordinates {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column private int x; // Максимальное значение поля: 988

  @Column(nullable = false)
  private Integer y; // Поле не может быть null
}
