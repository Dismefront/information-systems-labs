package org.dismefront.data.address;

import org.dismefront.data.location.Location;

public class Address {
    private String zipCode; //Длина строки не должна быть больше 13, Поле не может быть null
    private Location town; //Поле может быть null
}
