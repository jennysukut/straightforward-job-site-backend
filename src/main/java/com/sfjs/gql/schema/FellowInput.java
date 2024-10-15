package com.sfjs.gql.schema;

import lombok.Getter;
import lombok.Setter;

public class FellowInput {

  @Getter
  @Setter
  Long id;

  @Getter
  @Setter
  String name;

  @Getter
  @Setter
  String email;

  @Getter
  @Setter
  Boolean betaTester;

  @Getter
  @Setter
  Boolean collaborator;

  @Getter
  @Setter
  String message;

  @Getter
  @Setter
  Boolean referralPartner;

  @Getter
  @Setter
  String referralCode;
}
