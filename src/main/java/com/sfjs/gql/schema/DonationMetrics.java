package com.sfjs.gql.schema;

import lombok.Getter;
import lombok.Setter;

public class DonationMetrics {

  @Getter
  @Setter
  String totalDonations;

  @Getter
  @Setter
  String fellowDonations;

  @Getter
  @Setter
  String businessDonations;
}
