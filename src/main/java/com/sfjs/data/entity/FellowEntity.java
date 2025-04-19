package com.sfjs.data.entity;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
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
import jakarta.persistence.PostLoad;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "fellow")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class FellowEntity extends Fellow {

  @PostLoad
  private void postLoad() {
    LocalDateTime startOfToday = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
    ZoneId zone = ZoneId.systemDefault();

    // Get the offset for this ZoneId at the current moment
    ZoneOffset offset = zone.getRules().getOffset(startOfToday);

    // Now use the offset
    OffsetDateTime offsetDateTime = startOfToday.atOffset(offset);
    getJobApplications().forEach(app -> {
      if (app.getCreatedAt().isAfter(offsetDateTime)) {
        this.dailyApplications.add(app);
      }
    });
  }

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
  private Set<JobListingEntity> savedJobs = Set.of();
}
