package com.sfjs.gql.schema;

import lombok.Getter;
import lombok.Setter;

public class FellowMetrics {

  @Getter
  @Setter
  Long signups;

  @Getter
  @Setter
  Long collaborators;

  @Getter
  @Setter
  Long betaTesters;

  @Getter
  @Setter
  Long referralPartners;
}
