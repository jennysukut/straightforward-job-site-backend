package com.sfjs.gql.schema;

import lombok.Getter;
import lombok.Setter;

public class PaymentResult {

  @Getter
  @Setter
  private boolean success;

  @Getter
  @Setter
  private String message;
}
