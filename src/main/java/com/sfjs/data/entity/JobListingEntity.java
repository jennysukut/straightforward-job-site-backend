package com.sfjs.data.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sfjs.data.core.JobListing;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

/**
 * Contains the entity-specific declarations of fields
 * not sharable between entity and non-entity job listing classes
 *
 * @author carl
 *
 */
@Entity(name = "job_listing")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class JobListingEntity extends JobListing {

  //@Getter @Setter HybridDetailsData hybridDetails; //?: any;
  @Getter
  @Setter
  @Transient
  private boolean saved;

  @Getter
  @Setter
  @OneToMany(mappedBy = "jobListing")
  List<InterviewProcessEntity> interviewProcess; //?: Array<any>;

  @Getter
  @Setter
  @ManyToOne(optional = false)
  @JsonIgnore
  @JoinColumn(name = "business_id", unique = false)
  private BusinessEntity business;

  @Getter
  @Setter
  @JsonIgnore
  @OneToMany(mappedBy = "jobListing")
  private List<JobApplicationEntity> applications = new ArrayList<>();

  @Getter
  @Setter
  @JsonIgnore
  @ManyToMany(mappedBy = "savedJobs")
  private Set<FellowEntity> fellows = new HashSet<>();
}
