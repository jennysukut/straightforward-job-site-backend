package com.sfjs.dto;

import lombok.Getter;
import lombok.Setter;

public class PaymentResultInput {
  @Getter
  @Setter
  private long paymentId;

  @Getter
  @Setter
  private String hash;

  @Getter
  @Setter
  private TransactionDataInput data;
}
