package org.dismefront.data.address;

import jakarta.persistence.*;
import lombok.Data;
import org.dismefront.data.location.Location;

@Entity
@Table(name = "IS1_ADDRESS")
@Data
public class Address {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 13)
  private String zipCode; // Длина строки не должна быть больше 13, Поле не может быть null

  @JoinColumn
  @OneToOne(cascade = CascadeType.ALL)
  private Location town; // Поле может быть null
}
