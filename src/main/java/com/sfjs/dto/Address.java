package com.sfjs.dto;

import com.sfjs.entity.AddressEntity;

import lombok.Getter;
import lombok.Setter;

public class Address extends BaseData {

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

  public Address() {
  }

  public Address(AddressEntity entity) {
    if (entity != null) {
      this.setId(entity.getId());
      this.setName(entity.getName());
      this.setLabel(entity.getLabel());
      this.setStreetAddress(entity.getStreetAddress());
      this.setSecondLine(entity.getSecondLine());
      this.setCity(entity.getCity());
      this.setState(entity.getState());
      this.setCountry(entity.getCountry());
      this.setZipCode(entity.getZipCode());
      this.setZipPlus4(entity.getZipPlus4());
      this.setLandmark(entity.getLandmark());
    }
  }

}
