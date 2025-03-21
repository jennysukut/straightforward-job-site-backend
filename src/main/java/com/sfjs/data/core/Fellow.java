package com.sfjs.data.core;

import com.sfjs.data.NamedBaseObject;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class Fellow extends NamedBaseObject {

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
  private Boolean betaTester;
}
