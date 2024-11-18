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
    private String name; // Поле не может быть null, Строка не может быть пустой

    private Coordinates coordinates; // Поле не может быть null

    private java.time.ZonedDateTime creationDate; // Поле не может быть null, Значение этого поля должно генерироваться автоматически

    private UnitOfMeasure unitOfMeasure; // Поле не может быть null

    private Organization manufacturer; // Поле не может быть null

    private long price; // Значение поля должно быть больше 0
    private float manufactureCost;
    private int rating; // Значение поля должно быть больше 0
    private String partNumber; // Длина строки не должна быть больше 49, Поле не может быть null

    private Person owner; // Поле может быть null

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
