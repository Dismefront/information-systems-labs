package org.dismefront.data.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.dismefront.data.coordinates.Coordinates;
import org.dismefront.data.organization.Organization;
import org.dismefront.data.person.Person;
import org.dismefront.data.shared.UnitOfMeasure;

@Data
@AllArgsConstructor
public class ProductManaged {
  private Long id;
  private String name;

  private Coordinates coordinates;

  private java.time.ZonedDateTime creationDate;

  private UnitOfMeasure unitOfMeasure;

  private Organization manufacturer;

  private long price;
  private float manufactureCost;
  private int rating;
  private String partNumber;

  private Person owner;

  private Boolean editable;

  public ProductManaged(Product product) {
    this.id = product.getId();
    this.name = product.getName();
    this.coordinates = product.getCoordinates();
    this.creationDate = product.getCreationDate();
    this.unitOfMeasure = product.getUnitOfMeasure();
    this.manufacturer = product.getManufacturer();
    this.price = product.getPrice();
    this.manufactureCost = product.getManufactureCost();
    this.rating = product.getRating();
    this.partNumber = product.getPartNumber();
    this.owner = product.getOwner();
  }

  public ProductManaged() {}
}
