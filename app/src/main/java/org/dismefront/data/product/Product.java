package org.dismefront.data.product;

import jakarta.persistence.*;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dismefront.data.coordinates.Coordinates;
import org.dismefront.data.organization.Organization;
import org.dismefront.data.person.Person;
import org.dismefront.data.shared.UnitOfMeasure;

@Entity
@Table(name = "IS1_PRODUCT")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @JoinColumn(
      name = "coordinates_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_product_coordinates"))
  @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  private Coordinates coordinates;

  @Column(nullable = false)
  private ZonedDateTime creationDate;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private UnitOfMeasure unitOfMeasure;

  @JoinColumn(
      name = "manufacturer_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_product_manufacturer"))
  @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  private Organization manufacturer;

  @Column private long price;
  @Column private float manufactureCost;
  @Column private int rating;

  @Column private String partNumber;

  @JoinColumn(name = "owner_id", foreignKey = @ForeignKey(name = "fk_product_owner"))
  @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  private Person owner;
}
