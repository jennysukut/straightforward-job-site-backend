package com.sfjs.dto;

import lombok.Getter;
import lombok.Setter;

public class Address extends BaseBody {

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
