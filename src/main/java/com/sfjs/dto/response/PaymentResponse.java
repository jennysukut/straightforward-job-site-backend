package com.sfjs.dto.response;

import com.sfjs.entity.PaymentStatus;

import lombok.Getter;
import lombok.Setter;

public class PaymentResponse extends BaseResponse {

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
  private BusinessResponse business;

  @Getter
  @Setter
  private FellowResponse fellow;

  @Getter
  @Setter
  private PaymentStatus status;

  @Getter
  @Setter
  private String checkoutToken;
}
