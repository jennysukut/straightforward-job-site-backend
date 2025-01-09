package com.sfjs.data;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class JobApplicationData extends BaseJobApplicationData {

//  message: String
  @Getter @Setter private Long applicant; // : ID
  @Getter @Setter private Long jobListingId; // : ID
  @Getter @Setter private String business; // : String
  @Getter @Setter private Long businessId; // : ID
  @Getter @Setter private String dateOfApp; // : String
//  appStatus: String
  @Getter @Setter private List<JobApplicationNoteData> businessNote; //  businessNote: [String]
  @Getter @Setter private List<JobApplicationNoteData> fellowNote; //  fellowNote: [String]
  @Getter @Setter private List<InterviewAppointmentData> appointments; // : [InterviewAppointment]
}
