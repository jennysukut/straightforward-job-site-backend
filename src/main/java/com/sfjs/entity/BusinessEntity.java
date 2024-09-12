package com.sfjs.entity;

import java.util.Set;

import com.sfjs.dto.Business;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
  @ManyToOne(fetch = FetchType.EAGER, optional = true)
  @JoinColumn(name = "contact_info_id", unique = false)
  private ContactInfoEntity contactInfo;

  public BusinessEntity() {
  }

  public BusinessEntity(Business business) {
    this.aboutSection = business.getAboutSection();
    this.account = new AccountEntity();
    this.account.setEmail(business.getEmail());
    this.account.setEnabled(true);
    this.account.setName(business.getUserName());
    this.account.setPassword(business.getPassword());
    // TODO consider getting rid of role entities
    RoleEntity roleEntity = new RoleEntity();
    roleEntity.setName("BUSINESS");
    this.account.setRoles(Set.of(roleEntity));
    this.contactInfo = new ContactInfoEntity();
    this.contactInfo.setWebsite(business.getWebsite());
    this.contactInfo.setPhoneNumber(business.getPhoneNumber());
    this.contactInfo.setSocials(business.getSocials());
    this.missionAndVision = business.getMissionAndVision();
    this.name = business.getBusinessName();
    this.smallBio = business.getSmallBio();
  }
}
