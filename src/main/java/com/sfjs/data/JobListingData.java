package com.sfjs.data;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class JobListingData extends BaseData {

  @Getter @Setter String jobTitle; //?: string;
  @Getter @Setter String businessName; //?: string;
  @Getter @Setter String applicationLimit; //?: string;
  // this job number will probably get replaced by an
  // auto-generated id made by sending details to the server?
  @Getter @Setter Long jobNumber; //?: number;
  @Getter @Setter String positionType; //?: string;
  @Getter @Setter String positionSummary; //?: string;
  @Getter @Setter List<String> nonNegParams; //?: Array<string>;
//  Object payDetails; //?: any;
  @Getter @Setter String locationOption; //?: string;
  @Getter @Setter String idealCandidate; //?: string;
//  Object hybridDetails; //?: any;
//  Object experienceLevel; //?: any;
  @Getter @Setter List<String> preferredSkills; //?: Array<string>;
  @Getter @Setter String moreAboutPosition; //?: string;
//  Object responsibilities; //?: any;
  @Getter @Setter List<String> perks; //?: Array<string>;
//  List<Object> interviewProcess; //?: Array<any>;
  @Getter @Setter String location; //?: string;
  @Getter @Setter String country; //?: string;
//  Boolean jobIsBeingEdited; //?: boolean;
}
