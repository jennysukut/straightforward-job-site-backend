package com.sfjs.data;

import lombok.Getter;
import lombok.Setter;

/**
 * Contains the non-entity-specific declarations of fields
 * not sharable between entity and non-entity interview appointment classes
 *
 * @author carl
 *
 */
public class InterviewAppointmentData extends BaseInterviewAppointmentData {

  @Getter @Setter private String businessName;
  @Getter @Setter private String jobId;
}
