package com.sfjs.dto;

import lombok.Getter;
import lombok.Setter;

public class Fellow extends BaseBody {

  @Getter
  @Setter
  private String message;

  @Getter
  @Setter
  private String referralCode;

  @Getter
  @Setter
  private boolean referralPartner;

  @Getter
  @Setter
  private String email;

  @Getter
  @Setter
  private Boolean betaTester;
}
