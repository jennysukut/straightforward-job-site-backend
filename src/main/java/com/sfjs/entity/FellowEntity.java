package com.sfjs.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "fellow")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class FellowEntity extends BaseEntity {

  @Getter
  @Setter
  private String firstName;

  @Getter
  @Setter
  private String lastName;
}
