package org.dismefront.data.organization;

import jakarta.persistence.*;
import lombok.Data;
import org.dismefront.data.address.Address;

@Entity
@Table(name = "IS1_ORGANIZATION")
@Data
public class Organization {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long
      id; // Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

  @Column(nullable = false)
  private String name; // Поле не может быть null, Строка не может быть пустой

  @JoinColumn(nullable = false)
  @OneToOne(cascade = CascadeType.ALL)
  private Address officialAddress; // Поле не может быть null

  @Column private int annualTurnover; // Значение поля должно быть больше 0
  @Column private Long employeesCount; // Поле может быть null, Значение поля должно быть больше 0

  @Column
  private String fullName; // Значение этого поля должно быть уникальным, Поле может быть null

  @JoinColumn(nullable = false)
  @OneToOne(cascade = CascadeType.ALL)
  private Address postalAddress; // Поле не может быть null
}
