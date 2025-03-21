package com.sfjs.data.core;

import com.sfjs.data.NamedBaseObject;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class Business extends NamedBaseObject {

  @Getter
  @Setter
  private Boolean betaTester;

  @Getter
  @Setter
  private Boolean earlySignup;

  @Getter
  @Setter
  private String contactName;

  @Getter
  @Setter
  private String referral;
}
