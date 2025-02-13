package com.sfjs.data;

import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

public class PaymentData extends BasePaymentData {

  @Getter
  @Setter
  @Transient
  private String checkoutToken; // from helcim - don't store

  @Getter
  @Setter
  private BusinessData business;

  @Getter
  @Setter
  private FellowData fellow;

  @Getter
  @Setter
  private String secretToken; // from helcim - store encrypted
}
