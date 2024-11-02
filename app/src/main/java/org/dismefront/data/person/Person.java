package org.dismefront.data.person;

import org.dismefront.data.location.Location;
import org.dismefront.data.shared.Color;
import org.dismefront.data.shared.Country;

public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Color eyeColor; //Поле не может быть null
    private Color hairColor; //Поле может быть null
    private Location location; //Поле не может быть null
    private Long height; //Поле может быть null, Значение поля должно быть больше 0
    private Country nationality; //Поле не может быть null
}
