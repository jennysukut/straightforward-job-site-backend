package com.sfjs.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "contact_info")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ContactInfoEntity extends BaseEntity {

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
