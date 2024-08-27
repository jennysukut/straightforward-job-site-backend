package com.sfjs.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "fellow")
public class FellowEntity {

  // Every entity needs an id
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @Getter
  @Setter
  private Long id;

  // Every entity needs a name
  @Getter
  @Setter
  String name;

  // Every entity needs a label
  @Getter
  @Setter
  private String label;

  @Getter
  @Setter
  private String firstName;

  @Getter
  @Setter
  private String lastName;

  @Getter
  @Setter
  private String email;

  @Getter
  @Setter
  private String password;
}
