package com.sfjs.entity;

import com.sfjs.dto.Address;
import com.sfjs.dto.BaseBody;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "address")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class AddressEntity extends BaseEntity<AddressEntity, Address> {

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
  public <B extends BaseBody<?, ?>> void refresh(B body) {
    super.refresh(body);
    if (body instanceof Address) {
      Address addressBody = (Address) body;
      if (addressBody.getStreetAddress() != null) {
        this.setStreetAddress(addressBody.getStreetAddress());
      }
      if (addressBody.getSecondLine() != null) {
        this.setSecondLine(addressBody.getSecondLine());
      }
      if (addressBody.getCity() != null) {
        this.setCity(addressBody.getCity());
      }
      if (addressBody.getState() != null) {
        this.setState(addressBody.getState());
      }
      if (addressBody.getCountry() != null) {
        this.setCountry(addressBody.getCountry());
      }
      if (addressBody.getZipCode() != null) {
        this.setZipCode(addressBody.getZipCode());
      }
      if (addressBody.getZipPlus4() != null) {
        this.setZipPlus4(addressBody.getZipPlus4());
      }
      if (addressBody.getLandmark() != null) {
        this.setLandmark(addressBody.getLandmark());
      }
    }
  }
}