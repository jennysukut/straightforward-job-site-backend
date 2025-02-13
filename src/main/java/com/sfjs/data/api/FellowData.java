package com.sfjs.data.api;

import java.util.ArrayList;
import java.util.List;

import com.sfjs.data.core.Fellow;

import lombok.Getter;
import lombok.Setter;

public class FellowData extends Fellow {

  @Getter
  @Setter
  private AccountData account;

  @Getter
  @Setter
  private List<PaymentData> payments = new ArrayList<>();

  @Getter
  @Setter
  private FellowProfileData profile;

  @Getter
  @Setter
  private List<JobApplicationData> jobApplications = new ArrayList<>();
}
