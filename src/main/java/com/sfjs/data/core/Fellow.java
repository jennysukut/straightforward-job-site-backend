package com.sfjs.data.core;

import com.sfjs.data.BaseObject;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class Fellow extends BaseObject {

  @Getter
  @Setter
  private boolean collaborator;

  @Getter
  @Setter
  private String message;

  @Getter
  @Setter
  private boolean referralPartner;

  @Getter
  @Setter
  private String referralCode;

  @Getter
  @Setter
  private boolean betaTester;
}
