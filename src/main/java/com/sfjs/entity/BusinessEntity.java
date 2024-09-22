package com.sfjs.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "business")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BusinessEntity extends BaseEntity {

  @Getter
  @Setter
  @ManyToOne(optional = false)
  @JoinColumn(name = "account_id", unique = false)
  private AccountEntity account;

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

  @Getter
  @Setter
  @JsonIgnore
  @OneToMany(mappedBy = "business")
  private List<PaymentEntity> payments = new ArrayList<>();
}
