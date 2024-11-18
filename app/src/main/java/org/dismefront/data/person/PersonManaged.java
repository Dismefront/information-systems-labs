package org.dismefront.data.person;

import lombok.Data;
import org.dismefront.data.location.Location;
import org.dismefront.data.shared.Color;
import org.dismefront.data.shared.Country;

@Data
public class PersonManaged {
    private Long id;
    private String name;
    private Color eyeColor;
    private Color hairColor;
    private Location location;
    private Long height;
    private Country nationality;
    private Boolean editable;

    public PersonManaged(Long id, String name, Color eyeColor, Color hairColor, Location location, Long height, Country nationality, Boolean editable) {
        this.id = id;
        this.name = name;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.location = location;
        this.height = height;
        this.nationality = nationality;
        this.editable = editable;
    }

    public PersonManaged() {}
}
