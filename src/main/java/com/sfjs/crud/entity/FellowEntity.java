package com.sfjs.crud.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "fellow")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class FellowEntity extends BaseEntity {

  @Getter
  @Setter
  @OneToOne(optional = true)
  @JoinColumn(name = "account_id", unique = true)
  private AccountEntity account;

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

  @Getter
  @Setter
  @JsonIgnore
  @OneToMany(mappedBy = "fellow")
  private List<PaymentEntity> payments = new ArrayList<>();
}
