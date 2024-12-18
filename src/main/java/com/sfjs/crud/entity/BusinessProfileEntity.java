package com.sfjs.crud.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sfjs.data.BusinessProfileData;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
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
@Entity(name = "business_profile")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BusinessProfileEntity extends BusinessProfileData {

  @Getter
  @Setter
  @OneToOne(optional = true)
  @JsonIgnore
  @JoinColumn(name = "business_id", unique = true)
  private BusinessEntity business;
}
