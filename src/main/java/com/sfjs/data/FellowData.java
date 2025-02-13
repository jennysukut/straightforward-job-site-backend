package com.sfjs.data;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class FellowData extends BaseFellowData {

  @Getter
  @Setter
  private AccountData account;

  @Getter
  @Setter
  private List<PaymentData> payments = new ArrayList<>();

  @Getter
  @Setter
  private ExtendedProfileData profile;

  @Getter
  @Setter
  private List<JobApplicationData> jobApplications = new ArrayList<>();
}
