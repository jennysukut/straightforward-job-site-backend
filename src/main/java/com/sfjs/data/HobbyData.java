package com.sfjs.data;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * Contains fields that can be shared between
 * entity and non-entity hobby classes
 *
 * The mapped super class annotation means these fields
 * will correspond to database columns
 *
 * @author carl
 *
 */
@MappedSuperclass
public class HobbyData extends ProfileElementData {

  @Getter @Setter private String hobbyTitle;
  @Getter @Setter private String howLong;
}
