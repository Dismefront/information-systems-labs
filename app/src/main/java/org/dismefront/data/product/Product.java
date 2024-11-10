package org.dismefront.data.product;

import jakarta.persistence.*;
import org.dismefront.data.coordinates.Coordinates;
import org.dismefront.data.organization.Organization;
import org.dismefront.data.person.Person;
import org.dismefront.data.shared.UnitOfMeasure;

@Entity
@Table(name="IS1_PRODUCT")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; //Поле не может быть null, Строка не может быть пустой
    @JoinColumn(nullable = false)
    @OneToOne(cascade = CascadeType.ALL)
    private Coordinates coordinates; //Поле не может быть null
    @Column(nullable = false)
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @Column(nullable = false)
    private UnitOfMeasure unitOfMeasure; //Поле не может быть null
    @JoinColumn(name="manufacturer_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Organization manufacturer; //Поле не может быть null
    @Column
    private long price; //Значение поля должно быть больше 0
    @Column
    private float manufactureCost;
    @Column
    private int rating; //Значение поля должно быть больше 0
    @Column
    private String partNumber; //Длина строки не должна быть больше 49, Поле не может быть null

    @JoinColumn(name="owner_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Person owner; //Поле может быть null
}
