package org.dismefront.data.location;

import lombok.Data;

@Data
public class LocationManaged {
    private Long id;
    private long x;
    private double y;
    private Float z;
    private Boolean editable;

    public LocationManaged(Long id, long x, double y, Float z, Boolean editable) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.editable = editable;
    }

    public LocationManaged() {}
}
