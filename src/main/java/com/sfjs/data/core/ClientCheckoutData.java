package com.sfjs.data.core;

import lombok.Getter;
import lombok.Setter;

public class ClientCheckoutData {
  @Getter @Setter private Long id;
  @Getter @Setter private PaymentStatus status;
  @Getter @Setter private String checkoutToken;
}
