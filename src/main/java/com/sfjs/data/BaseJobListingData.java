package com.sfjs.data;

import java.util.List;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class BaseJobListingData extends BaseData {

  @Getter @Setter String jobTitle; //?: string;
  @Getter @Setter String businessName; //?: string;
  @Getter @Setter String applicationLimit; //?: string;
  // this job number will probably get replaced by an
  // auto-generated id made by sending details to the server?
  @Getter @Setter Long jobNumber; //?: number;
  @Getter @Setter String positionType; //?: string;
  @Getter @Setter String positionSummary; //?: string;
  @Getter @Setter List<String> nonNegParams; //?: Array<string>;
//  @Getter @Setter PayDetailsData payDetails; //?: any;
  @Getter @Setter String locationOption; //?: string;
  @Getter @Setter String idealCandidate; //?: string;
//  @Getter @Setter HybridDetailsData hybridDetails; //?: any;
  @Getter @Setter List<String> experienceLevel; //?: any;
  @Getter @Setter List<String> preferredSkills; //?: Array<string>;
  @Getter @Setter String moreAboutPosition; //?: string;
  @Getter @Setter List<String> responsibilities; //?: any;
  @Getter @Setter List<String> perks; //?: Array<string>;
//  @Getter @Setter List<InterviewProcessData> interviewProcess; //?: Array<any>;
  @Getter @Setter String location; //?: string;
  @Getter @Setter String country; //?: string;
//  Boolean jobIsBeingEdited; //?: boolean;
  @Getter @Setter String roundNumber;
}
