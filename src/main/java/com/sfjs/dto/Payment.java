package com.sfjs.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

public class Payment extends BaseBody {

  @Getter
  @Setter
  private String paymentType; // example: "purchase"

  @Getter
  @Setter
  private String amount; // example: "0.01"

  @Getter
  @Setter
  private String currency; // example: "CAD"

  @Getter
  @Setter
  @Transient
  private String checkoutToken; // from helcim - don't store

  @Getter
  @Setter
  private String businessName;

  @Getter
  @Setter
  private Business business;

  @Getter
  @Setter
  private String fellowName;

  @Getter
  @Setter
  private Fellow fellow;

  @Getter
  @Setter
  private String email;

  @Getter
  @Setter
  @JsonIgnore
  private String SALT;

  @Getter
  @Setter
  @JsonIgnore
  private String secretToken;

  @Getter
  @Setter
  private String status;
}
