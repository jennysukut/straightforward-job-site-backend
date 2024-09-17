package com.sfjs.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "business")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BusinessEntity extends BaseEntity {

  @Getter
  @Setter
  @OneToOne(optional = false)
  @JoinColumn(name = "account_id", unique = true)
  private AccountEntity account;

  @Getter
  @Setter
  private String smallBio;

  @Getter
  @Setter
  private String missionAndVision;

  @Getter
  @Setter
  private String aboutSection;

  @Getter
  @Setter
  private String website;

  @Getter
  @Setter
  private String phoneNumber;

  @Getter
  @Setter
  private String socials;
}
