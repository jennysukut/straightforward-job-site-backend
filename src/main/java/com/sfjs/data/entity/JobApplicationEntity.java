package com.sfjs.data.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sfjs.data.core.JobApplication;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "job_application")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class JobApplicationEntity extends JobApplication {

  // businessNote: [String]
  // fellowNote: [String]
  @Getter
  @Setter
  @JsonIgnore
  @OneToMany(mappedBy = "application")
  private List<JobApplicationNoteEntity> notes;

  @Getter
  @Setter
  @ManyToOne(optional = true)
  @JsonIgnore
  @JoinTable(name = "job_application_fellow",
    joinColumns = @JoinColumn(name = "job_application_id"), // Foreign key for JobApplicationEntity
    inverseJoinColumns = @JoinColumn(name = "fellow_id") // Foreign key for FellowEntity
  )
  private FellowEntity fellow;

  @Getter
  @Setter
  @OneToOne(optional = true)
  @JsonIgnore
  @JoinColumn(name = "job_listing_id", unique = false)
  private JobListingEntity jobListing;

  @Getter
  @Setter
  @JsonIgnore
  @OneToMany(mappedBy = "application")
  private List<InterviewAppointmentEntity> appointments = new ArrayList<>();
}
