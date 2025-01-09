package com.sfjs.crud.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sfjs.data.JobApplicationNoteData;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * Contains the entity-specific declarations of fields
 * not sharable between entity and non-entity appliation note classes
 *
 * @author carl
 *
 */
@Entity(name = "job_application_note")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class JobApplicationNoteEntity extends JobApplicationNoteData {

  @Getter
  @Setter
  @ManyToOne(optional = true)
  @JsonIgnore
  @JoinColumn(name = "application_id", unique = false)
  private JobApplicationEntity application;
}
