package com.sfjs.data;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * Contains fields that can be shared between
 * entity and non-entity award classes
 *
 * The mapped super class annotation means these fields
 * will correspond to database columns
 *
 * @author carl
 *
 */
@MappedSuperclass
public class AwardData extends ProfileElementData {

  @Getter @Setter private String awardTitle;
  @Getter @Setter private String awardDetails;
  @Getter @Setter private String givenBy;
}
