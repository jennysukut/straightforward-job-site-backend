package com.sfjs.dto;

import lombok.Getter;
import lombok.Setter;

public class Fellow extends BaseBody {

  @Getter
  @Setter
  private Boolean collaborator;

  @Getter
  @Setter
  private String message;

  @Getter
  @Setter
  private Boolean referralPartner;

  @Getter
  @Setter
  private String referralCode;

  @Getter
  @Setter
  private String email;

  @Getter
  @Setter
  private Boolean betaTester;

  @Getter
  @Setter
  private Account account;
}
