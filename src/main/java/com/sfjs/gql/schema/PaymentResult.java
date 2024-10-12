package com.sfjs.gql.schema;

import com.sfjs.crud.response.PaymentResponse;

import lombok.Getter;
import lombok.Setter;

public class PaymentResult {

  @Getter
  @Setter
  private boolean success;

  @Getter
  @Setter
  private String message;

  @Getter
  @Setter
  private PaymentResponse payment;
}
