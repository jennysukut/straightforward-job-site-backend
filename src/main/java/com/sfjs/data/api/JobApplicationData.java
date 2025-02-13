package com.sfjs.data.api;

import java.util.List;

import com.sfjs.data.core.JobApplication;
import com.sfjs.data.core.JobApplicationNote;

import lombok.Getter;
import lombok.Setter;

public class JobApplicationData extends JobApplication {

//  message: String
  @Getter @Setter private Long applicant; // : ID
  @Getter @Setter private Long jobListingId; // : ID
  @Getter @Setter private String business; // : String
  @Getter @Setter private Long businessId; // : ID
  @Getter @Setter private String dateOfApp; // : String
//  appStatus: String
  @Getter @Setter private List<JobApplicationNote> businessNote; //  businessNote: [String]
  @Getter @Setter private List<JobApplicationNote> fellowNote; //  fellowNote: [String]
  @Getter @Setter private List<InterviewAppointmentData> appointments; // : [InterviewAppointment]
}
