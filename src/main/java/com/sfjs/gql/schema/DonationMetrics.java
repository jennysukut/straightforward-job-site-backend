package com.sfjs.gql.schema;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

public class DonationMetrics {

  @Getter
  @Setter
  BigDecimal totalDonations;

  @Getter
  @Setter
  int fellowDonations;

  @Getter
  @Setter
  int businessDonations;

  @Getter
  @Setter
  int totalDonationsCount;
}
