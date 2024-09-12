package com.sfjs.dto;

import com.sfjs.entity.AccountEntity;
import com.sfjs.entity.BusinessEntity;
import com.sfjs.entity.ContactInfoEntity;

import lombok.Getter;
import lombok.Setter;

public class Business extends BaseBody {

  @Getter
  @Setter
  private String businessName;

  @Getter
  @Setter
  private String userName;

  @Getter
  @Setter
  private String password;

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
  private String industry;

  @Getter
  @Setter
  private String location;

  @Getter
  @Setter
  private String website;

  @Getter
  @Setter
  private String email;

  @Getter
  @Setter
  private String phoneNumber;

  @Getter
  @Setter
  private String socials;

  public Business() {
  }

  public Business(BusinessEntity entity) {
    super(entity);
    this.businessName = entity.getName();
    AccountEntity accountEntity = entity.getAccount();
    this.userName = accountEntity.getName();
    // Password is not returned to the client
//    this.password = accountEntity.getPassword(); // Not passed to client
    this.smallBio = entity.getSmallBio();
    this.missionAndVision = entity.getMissionAndVision();
    this.aboutSection = entity.getAboutSection();
    // TODO industry
//    this.industry = entity.getIndustry();
    // TODO location
//    AddressEntity address = null; // entity.getAddress();
//    this.location = String.format("%s %s", address.getCity(), address.getState());
    ContactInfoEntity contactInfoEntity = entity.getContactInfo();
    this.website = contactInfoEntity.getWebsite();
    this.email = accountEntity.getEmail();
    this.phoneNumber = contactInfoEntity.getPhoneNumber();
    this.socials = contactInfoEntity.getSocials();
  }
}
