package com.sfjs.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.sfjs.data.BaseJobListingData;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class JobListingEntity extends BaseJobListingData {

  //@Getter @Setter PayDetailsData payDetails; //?: any;
  // This is not sharable because we are flattening it
  @Getter @Setter private float payScaleMin; // : Float
  @Getter @Setter private float payScaleMax; // : Float
  @Getter @Setter private String payOption; // : String
  //@Getter @Setter HybridDetailsData hybridDetails; //?: any;
  // This is not sharable because we are flattening it
  @Getter @Setter private String daysInOffice; // : String
  @Getter @Setter private String daysRemote; // : String

  @Getter
  @Setter
  @OneToMany(mappedBy = "jobListing")
  List<InterviewProcessEntity> interviewProcess; //?: Array<any>;

  @Getter
  @Setter
  @OneToOne(optional = true)
  @JsonIgnore
  @JoinColumn(name = "business_id", unique = true)
  private BusinessEntity business;

  @Getter
  @Setter
  @JsonIgnore
  @OneToMany(mappedBy = "jobListing")
  private List<JobApplicationEntity> jobApplications = new ArrayList<>();
}
