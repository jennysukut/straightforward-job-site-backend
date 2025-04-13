package com.sfjs.data.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sfjs.data.core.InterviewProcess;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

/**
 * Contains the entity-specific annotations to
 * make this an entity class
 * All fields are defined in sharable parent classes
 *
 * @author carl
 *
 */
@Entity(name = "interview_process")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@SQLRestriction(value = "deleted_at IS NULL")
public class InterviewProcessEntity extends InterviewProcess {

  @Getter
  @Setter
  @JsonIgnore
  @OneToMany(mappedBy = "interviewProcess")
  private List<InterviewAppointmentEntity> appointments = new ArrayList<>();
}
