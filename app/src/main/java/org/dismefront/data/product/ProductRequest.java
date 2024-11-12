package org.dismefront.data.product;

import org.dismefront.data.organization.Organization;
import org.dismefront.data.shared.UnitOfMeasure;

import java.time.ZonedDateTime;
import java.util.Optional;

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

    public Long getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCoordinatesId() {
        return coordinatesId;
    }

    public void setCoordinatesId(Long coordinatesId) {
        this.coordinatesId = coordinatesId;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public float getManufactureCost() {
        return manufactureCost;
    }

    public void setManufactureCost(float manufactureCost) {
        this.manufactureCost = manufactureCost;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public Optional<Long> getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Optional<Long> ownerId) {
        this.ownerId = ownerId;
    }
}
