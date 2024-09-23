package com.sfjs.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
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
  private String firstName;

  @Getter
  @Setter
  private String lastName;

  @Getter
  @Setter
  private Boolean betaTester;
}
