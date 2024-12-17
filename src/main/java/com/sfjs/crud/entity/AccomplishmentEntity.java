package com.sfjs.crud.entity;

import org.hibernate.annotations.SQLRestriction;

import com.sfjs.data.AccomplishmentData;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

/**
 * Contains the entity-specific annotations to
 * make this an entity class
 * All fields are defined in sharable parent classes
 *
 * @author carl
 *
 */
@Entity(name = "accomplishment")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@SQLRestriction(value = "deleted_at IS NULL")
public class AccomplishmentEntity extends AccomplishmentData {

}
