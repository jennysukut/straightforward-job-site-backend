package com.sfjs.data.core;

import com.sfjs.data.entity.ProfileElementData;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * Contains fields that can be shared between
 * entity and non-entity experience-level classes
 *
 * The mapped super class annotation means these fields
 * will correspond to database columns
 *
 * @author carl
 *
 */
@MappedSuperclass
public class ExperienceLevel extends ProfileElementData {

  @Getter @Setter private String experienceLevel;
  @Getter @Setter private String expLevelSkill;
  @Getter @Setter private String skillYears;
}
