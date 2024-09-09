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
}
