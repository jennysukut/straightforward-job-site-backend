package com.sfjs.data.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sfjs.data.core.Fellow;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "fellow")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class FellowEntity extends Fellow {

  @Getter
  @Setter
  @OneToOne(optional = true)
  @JoinColumn(name = "account_id", unique = true)
  private AccountEntity account;

  @Getter
  @Setter
  @JsonIgnore
  @OneToMany(mappedBy = "fellow")
  private List<PaymentEntity> payments = new ArrayList<>();

  @Getter
  @Setter
  @OneToOne(mappedBy = "fellow", optional = true)
  private FellowProfileEntity profile;

  @Getter
  @Setter
  @JsonIgnore
  @OneToMany(mappedBy = "fellow")
  private List<JobApplicationEntity> jobApplications = new ArrayList<>();

  @Getter
  @Setter
  @JsonIgnore
  @Transient
  private List<JobApplicationEntity> dailyApplications = new ArrayList<>();

  @Getter
  @Setter
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "saved_jobs", joinColumns = @JoinColumn(name = "fellow_id"), inverseJoinColumns = @JoinColumn(name = "job_listing_id"))
  private Set<JobListingEntity> savedJobs;
}
