package org.dismefront.data.organization;

import jakarta.persistence.*;
import org.dismefront.data.address.Address;

@Entity
@Table(name = "IS1_ORGANIZATION")
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

  public Address getOfficialAddress() {
    return officialAddress;
  }

  public void setOfficialAddress(Address officialAddress) {
    this.officialAddress = officialAddress;
  }

  public int getAnnualTurnover() {
    return annualTurnover;
  }

  public void setAnnualTurnover(int annualTurnover) {
    this.annualTurnover = annualTurnover;
  }

  public Long getEmployeesCount() {
    return employeesCount;
  }

  public void setEmployeesCount(Long employeesCount) {
    this.employeesCount = employeesCount;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public Address getPostalAddress() {
    return postalAddress;
  }

  public void setPostalAddress(Address postalAddress) {
    this.postalAddress = postalAddress;
  }
}
