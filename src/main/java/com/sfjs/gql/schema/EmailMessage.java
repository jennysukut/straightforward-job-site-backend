package com.sfjs.gql.schema;

import lombok.Getter;
import lombok.Setter;

public class EmailMessage {

  @Getter
  @Setter
  private String to;

  @Getter
  @Setter
  private String subject;

  @Getter
  @Setter
  private String text;
}
