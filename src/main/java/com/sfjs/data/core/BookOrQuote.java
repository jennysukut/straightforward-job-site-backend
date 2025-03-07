package com.sfjs.data.core;

import com.sfjs.data.entity.ProfileElementData;

import jakarta.persistence.MappedSuperclass;

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

}
