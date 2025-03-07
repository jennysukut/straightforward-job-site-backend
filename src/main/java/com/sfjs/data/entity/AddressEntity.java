package com.sfjs.data.entity;

import com.sfjs.data.core.Address;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "address")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class AddressEntity extends Address {

//  @Getter
//  @Setter
//  private String streetAddress;
//
//  @Getter
//  @Setter
//  private String secondLine;
//
//  @Getter
//  @Setter
//  private String city;
//
//  @Getter
//  @Setter
//  private String state;
//
//  @Getter
//  @Setter
//  private String zipCode;
//
//  @Getter
//  @Setter
//  private String zipPlus4;
//
//  @Getter
//  @Setter
//  private String country;
//
//  @Getter
//  @Setter
//  private String landmark;
}