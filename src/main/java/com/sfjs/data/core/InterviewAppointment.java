package com.sfjs.data.core;

import com.sfjs.data.BaseObject;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class InterviewAppointment extends BaseObject {

  @Getter @Setter private String interviewStep;
  @Getter @Setter private String note;
}
