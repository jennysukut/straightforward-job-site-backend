package com.sfjs.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "address")
public class AddressEntity {

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
  private String streetAddress;

  @Getter
  @Setter
  private String secondLine;

  @Getter
  @Setter
  private String city;

  @Getter
  @Setter
  private String state;

  @Getter
  @Setter
  private String zipCode;

  @Getter
  @Setter
  private String zipPlus4;

  @Getter
  @Setter
  private String country;

  @Getter
  @Setter
  private String landmark;
}