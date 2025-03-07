package com.sfjs.data.core;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class HybridDetails {

  @Getter @Setter private String daysInOffice; // : String
  @Getter @Setter private String daysRemote; // : String
}
