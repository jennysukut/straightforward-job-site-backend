package com.sfjs.data;

import java.net.URL;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * Contains fields that can be shared between
 * entity and non-entity link classes
 *
 * The mapped super class annotation means these fields
 * will correspond to database columns
 *
 * @author carl
 *
 */
@MappedSuperclass
public class LinkData extends ProfileElementData {

  @Getter @Setter private String linkType;
  @Getter @Setter private URL link;
}
