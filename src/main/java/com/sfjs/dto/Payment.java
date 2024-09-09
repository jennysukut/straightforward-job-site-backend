package com.sfjs.dto;

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
  private Account account;
}
