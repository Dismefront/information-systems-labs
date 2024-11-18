package org.dismefront.api.person;

import lombok.Data;
import org.dismefront.data.location.Location;
import org.dismefront.data.shared.Color;
import org.dismefront.data.shared.Country;

@Data
public class PersonRequest {
    private String name;
    private Color eyeColor;
    private Color hairColor;
    private Long locationId;
    private Long height;
    private Country nationality;
}
