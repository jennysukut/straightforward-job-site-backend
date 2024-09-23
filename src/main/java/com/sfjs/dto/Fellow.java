package com.sfjs.dto;

import lombok.Getter;
import lombok.Setter;

public class Fellow extends BaseBody {

  @Getter
  @Setter
  private String firstName;

  @Getter
  @Setter
  private String lastName;

  @Getter
  @Setter
  private String email;

  @Getter
  @Setter
  private Boolean betaTester;
}
