package com.sfjs.gql.schema;

import lombok.Getter;
import lombok.Setter;

public class MetricsResult {

  @Getter
  @Setter
  FellowMetrics fellowMetrics;

  @Getter
  @Setter
  BusinessMetrics businessMetrics;

  @Getter
  @Setter
  DonationMetrics donationMetrics;
}
