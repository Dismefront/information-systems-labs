package org.dismefront.data;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@jakarta.persistence.Entity
@Table(name="entity")
@Data
public class Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String surname;

    public Entity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
