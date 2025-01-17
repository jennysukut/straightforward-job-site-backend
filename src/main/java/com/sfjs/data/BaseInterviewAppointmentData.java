package com.sfjs.data;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class BaseInterviewAppointmentData extends BaseData {

  @Getter @Setter private String interviewStep;
  @Getter @Setter private String note;
}
