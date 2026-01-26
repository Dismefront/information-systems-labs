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
      id;

  @Column(nullable = false)
  private String name;

  @JoinColumn(
      name = "official_address_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_organization_official_address"))
  @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  private Address officialAddress;

  @Column private int annualTurnover;
  @Column private Long employeesCount;

  @Column(name = "full_name", unique = true)
  private String fullName;

  @JoinColumn(
      name = "postal_address_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_organization_postal_address"))
  @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  private Address postalAddress;
}
