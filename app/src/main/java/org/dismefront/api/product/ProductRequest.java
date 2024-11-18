package org.dismefront.api.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.dismefront.data.shared.UnitOfMeasure;

import java.util.Optional;

@AllArgsConstructor
@Data
public class ProductRequest {
    private String name;
    private Long coordinatesId;
    private java.time.ZonedDateTime creationDate;
    private UnitOfMeasure unitOfMeasure;
    private Long manufacturerId;
    private long price;
    private float manufactureCost;
    private int rating;
    private String partNumber;
    private Optional<Long> ownerId;

    public ProductRequest(String name, Long coordinatesId, UnitOfMeasure unitOfMeasure,
                          Long manufacturerId, long price, float manufactureCost, int rating, String partNumber, Optional<Long> ownerId) {
        this.name = name;
        this.coordinatesId = coordinatesId;
        this.unitOfMeasure = unitOfMeasure;
        this.manufacturerId = manufacturerId;
        this.price = price;
        this.rating = rating;
        this.partNumber = partNumber;
        this.ownerId = ownerId;
        this.manufactureCost = manufactureCost;
    }
}
