package org.dismefront.data.coordinates;

import lombok.Data;

@Data
public class CoordinatesManaged {
    private Long id;
    private int x;
    private Integer y;
    private Boolean editable;

    public CoordinatesManaged(Long id, int x, Integer y, Boolean editable) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.editable = editable;
    }

    public CoordinatesManaged() {}
}
