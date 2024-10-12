package com.sfjs.gql.schema;

import lombok.Getter;
import lombok.Setter;

public class Result {
  @Getter
  @Setter
  private Boolean success;

  @Getter
  @Setter
  private String message;
}
