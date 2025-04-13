package com.sfjs.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sfjs.data.core.JobInterviewProcessStepAppointment;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * Contains the entity-specific declarations of fields
 * not sharable between entity and non-entity profile classes
 *
 * @author carl
 *
 */
@Entity(name = "interview_appointment")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class InterviewAppointmentEntity extends JobInterviewProcessStepAppointment {

  @Getter
  @Setter
  @OneToOne(optional = true)
  @JsonIgnore
  @JoinColumn(name = "application_id", unique = true)
  private JobApplicationEntity application;

  @Getter
  @Setter
  @ManyToOne(optional = false)
  @JsonIgnore
  @JoinColumn(name = "interview_process_id", unique = false)
  private InterviewProcessEntity interviewProcess;
}
