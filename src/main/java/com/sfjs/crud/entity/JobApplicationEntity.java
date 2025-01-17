package com.sfjs.crud.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sfjs.data.BaseJobApplicationData;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "job_application")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class JobApplicationEntity extends BaseJobApplicationData {

  // businessNote: [String]
  // fellowNote: [String]
  @Getter
  @Setter
  @JsonIgnore
  @OneToMany(mappedBy = "application")
  private List<JobApplicationNoteEntity> notes;

  @Getter
  @Setter
  @OneToOne(optional = true)
  @JsonIgnore
  @JoinColumn(name = "fellow_id", unique = false)
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
