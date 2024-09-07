package com.sfjs.dto;

import com.sfjs.entity.AddressEntity;
import com.sfjs.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

public class Address extends BaseBody<Address, AddressEntity> {

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

  @Override
  public <E extends BaseEntity<?, ?>> void refresh(E entity) {
    super.refresh(entity);
    if (entity instanceof AddressEntity) {
      AddressEntity addressEntity = (AddressEntity) entity;
      if (addressEntity.getStreetAddress() != null) {
        this.setStreetAddress(addressEntity.getStreetAddress());
      }
      if (addressEntity.getSecondLine() != null) {
        this.setSecondLine(addressEntity.getSecondLine());
      }
      if (addressEntity.getCity() != null) {
        this.setCity(addressEntity.getCity());
      }
      if (addressEntity.getState() != null) {
        this.setState(addressEntity.getState());
      }
      if (addressEntity.getCountry() != null) {
        this.setCountry(addressEntity.getCountry());
      }
      if (addressEntity.getZipCode() != null) {
        this.setZipCode(addressEntity.getZipCode());
      }
      if (addressEntity.getZipPlus4() != null) {
        this.setZipPlus4(addressEntity.getZipPlus4());
      }
      if (addressEntity.getLandmark() != null) {
        this.setLandmark(addressEntity.getLandmark());
      }
    }
  }
}
