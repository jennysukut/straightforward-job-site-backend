package com.sfjs.gql.schema;

import lombok.Getter;
import lombok.Setter;

public class BusinessInput {

  @Getter
  @Setter
  Long id;

  @Getter
  @Setter
  String business;

  @Getter
  @Setter
  String email;

  @Getter
  @Setter
  Boolean earlySignup;

  @Getter
  @Setter
  Boolean betaTester;

  @Getter
  @Setter
  String contactName;

  @Getter
  @Setter
  String referral;
}
