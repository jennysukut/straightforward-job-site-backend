package com.sfjs.data.api;

import com.sfjs.data.core.InterviewAppointment;

import lombok.Getter;
import lombok.Setter;

/**
 * Contains the non-entity-specific declarations of fields
 * not sharable between entity and non-entity interview appointment classes
 *
 * @author carl
 *
 */
public class InterviewAppointmentData extends InterviewAppointment {

  @Getter @Setter private String businessName;
  @Getter @Setter private String jobId;
}
