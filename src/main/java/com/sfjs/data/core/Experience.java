package com.sfjs.data.core;

import com.sfjs.data.api.ProfileElementData;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * Contains fields that can be shared between
 * entity and non-entity experience classes
 *
 * The mapped super class annotation means these fields
 * will correspond to database columns
 *
 * @author carl
 *
 */
@MappedSuperclass
public class Experience extends ProfileElementData {

  @Getter @Setter private String title;
  @Getter @Setter private String companyName;
  @Getter @Setter private String yearDetails;
  @Getter @Setter private String details;
}
