package com.sfjs.jpa.entity;

import java.util.List;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sfjs.data.BaseProfileData;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * Contains the entity-specific declarations of fields
 * not sharable between entity and non-entity profile classes
 *
 * @author carl
 *
 */
@Entity(name = "profile")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ExtendedProfileEntity extends BaseProfileData {

  static Logger logger = Logger.getLogger(ExtendedProfileEntity.class.getName());

  @Getter
  @Setter
  @OneToOne(optional = true)
  @JsonIgnore
  @JoinColumn(name = "fellow_id", unique = true)
  private FellowEntity fellow;

  @Getter
  @Setter
  @OneToMany(mappedBy = "profile")
  private List<ExperienceEntity> experience;

  @Getter
  @Setter
  @OneToMany(mappedBy = "profile")
  private List<EducationEntity> education;

  @Getter
  @Setter
  @OneToMany(mappedBy = "profile")
  private List<AwardEntity> awards;

  @Getter
  @Setter
  @OneToMany(mappedBy = "profile")
  private List<ExperienceLevelEntity> experienceLevels;

  @Getter
  @Setter
  @OneToMany(mappedBy = "profile")
  private List<AccomplishmentEntity> accomplishments;

  @Getter
  @Setter
  @OneToMany(mappedBy = "profile")
  private List<HobbyEntity> hobbies;

  @Getter
  @Setter
  @OneToMany(mappedBy = "profile")
  private List<BookOrQuoteEntity> bookOrQuote;

  @Getter
  @Setter
  @OneToMany(mappedBy = "profile")
  private List<LinkEntity> links;
}
