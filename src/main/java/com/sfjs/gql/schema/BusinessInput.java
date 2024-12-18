package com.sfjs.gql.schema;

import lombok.Getter;
import lombok.Setter;

public class BusinessInput {

  @Getter
  @Setter
  Long id;

  @Getter
  @Setter
  String businessName;

  @Getter
  @Setter
  String email;

  @Getter
  @Setter
  String password;

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
