package com.sfjs.dto;

import lombok.Getter;
import lombok.Setter;

public class PaymentResult {

  @Getter
  @Setter
  private boolean success;

  @Getter
  @Setter
  private boolean message;

  @Getter
  @Setter
  private Payment payment;
}
