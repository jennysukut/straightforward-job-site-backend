package com.sfjs.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sfjs.data.BaseObject;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * Contains fields that can be shared between
 * entity and non-entity profile element classes
 *
 * @author carl
 *
 */
@MappedSuperclass
public class ProfileElementData extends BaseObject {

  @Getter
  @Setter
  @JsonIgnore
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "profile_id", nullable = true)
  private FellowProfileEntity profile;
}
