package com.sfjs.data.core;

import java.time.LocalDateTime;

import com.sfjs.data.BaseObject;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class InterviewAppointment extends BaseObject {

  @Getter @Setter private LocalDateTime interviewDateAndTime;

  @Getter @Setter private String note;
}
