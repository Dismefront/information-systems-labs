package org.dismefront.data.address;

import lombok.Data;
import org.dismefront.data.location.Location;

@Data
public class AddressManaged {

    private Long id;
    private String zipCode;
    private Location town;
    private Boolean editable;

    public AddressManaged(Long id, String zipCode, Location town, Boolean editable) {
        this.id = id;
        this.zipCode = zipCode;
        this.town = town;
        this.editable = editable;
    }

    public AddressManaged() {}
}