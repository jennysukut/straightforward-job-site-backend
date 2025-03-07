package com.sfjs.data.entity;

import java.net.URL;

import org.hibernate.annotations.SQLRestriction;

import com.sfjs.data.core.Link;

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
@Entity(name = "link")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@SQLRestriction(value = "deleted_at IS NULL")
public class LinkEntity extends Link {

}
