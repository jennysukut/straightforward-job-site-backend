package com.sfjs.gql.schema;

import lombok.Getter;
import lombok.Setter;

public class FellowDonation {

  @Getter
  @Setter
  private String amount;

  @Getter
  @Setter
  private String name;

  @Getter
  @Setter
  private String email;
}
