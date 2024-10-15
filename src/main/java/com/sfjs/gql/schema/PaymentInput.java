package com.sfjs.gql.schema;

import lombok.Getter;
import lombok.Setter;

public class PaymentInput {

  @Getter
  @Setter
  FellowInput fellow;

  @Getter
  @Setter
  String amount;

  @Getter
  @Setter
  String currency;

  @Getter
  @Setter
  String email;

  @Getter
  @Setter
  String paymentType;

  @Getter
  @Setter
  String fellowName;

  @Getter
  @Setter
  String SALT;

  @Getter
  @Setter
  String secretToken;

  @Getter
  @Setter
  BusinessInput business;

  @Getter
  @Setter
  String businessName;
}
