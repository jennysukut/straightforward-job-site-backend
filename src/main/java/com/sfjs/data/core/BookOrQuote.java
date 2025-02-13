package com.sfjs.data.core;

import com.sfjs.data.api.ProfileElementData;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * Contains fields that can be shared between
 * entity and non-entity book-or-quote classes
 *
 * The mapped super class annotation means these fields
 * will correspond to database columns
 *
 * @author carl
 *
 */
@MappedSuperclass
public class BookOrQuote extends ProfileElementData {

  @Getter @Setter private String bookOrQuote;
  @Getter @Setter private String author;
}
