package org.dismefront.data.coordinates;

import jakarta.persistence.*;

@Entity
@Table(name = "IS1_COORDINATES")
public class Coordinates {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column private int x; // Максимальное значение поля: 988

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public Integer getY() {
    return y;
  }

  public void setY(Integer y) {
    this.y = y;
  }

  @Column(nullable = false)
  private Integer y; // Поле не может быть null
}
