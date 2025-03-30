package com.sfjs.data.core;

import java.util.List;

import com.sfjs.data.BaseObject;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class JobListingBase extends BaseObject {
  // Stored separately
  // applicants?: Array<string>;

   @Getter @Setter String jobTitle; //?: string;

  // Stored separately
  // businessName?: string;

  // Stored separately
  // businessId?: string;

  // Stored separately
  // applications?: Array<string>;

  @Getter @Setter String applicationLimit; //?: string;

  // Every entity has an id
  // jobId?: string;

  @Getter @Setter String positionType; //?: string;

  @Getter @Setter String positionSummary; //?: string;

  @Getter @Setter List<String> nonNegParams; //?: Array<string>;

  // Flattened
  // payDetails?: { payscaleMin?: number; payscaleMax?: number; payOption?: string; };
  @Getter @Setter float payscaleMin;
  @Getter @Setter float payscaleMax;
  @Getter @Setter String payOption;

  @Getter @Setter String locationOption; //?: string;

  @Getter @Setter String idealCandidate; //?: string;

  // Flattened
  // hybridDetails?: { daysInOffice?: string; daysRemote?: string; };
  @Getter @Setter String daysInOffice;
  @Getter @Setter String daysRemote;

  @Getter @Setter List<String> experienceLevel; //?: any;

  // Flattened
  // interviewer?: { name: string; details: string };
  @Getter @Setter String interviewerName;
  @Getter @Setter String interviewerDetails;

  @Getter @Setter List<String> preferredSkills; //?: Array<string>;

  @Getter @Setter String moreAboutPosition; //?: string;

  // Flattened
  // responsibilities?: Array<{ id?: number; responsibility?: string }> | [];
  @Getter @Setter List<String> responsibilities; //?: any;

  @Getter @Setter List<String> perks; //?: Array<string>;

  // Nested structure
//  interviewProcess?: Array<{
//    stage: string;
//    step: string;
//    details: string;
//    id: number;
//  }>;

  // Stored separately
  // @Getter @Setter String location; //?: string;

  // Stored separately
  // @Getter @Setter String country; //?: string;

  @Getter @Setter Integer roundNumber;
}
