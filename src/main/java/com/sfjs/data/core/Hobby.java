package com.sfjs.data.core;

import com.sfjs.data.entity.ProfileElementData;

import jakarta.persistence.MappedSuperclass;

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
public class Hobby extends ProfileElementData {

}
