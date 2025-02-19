package com.sfjs.data.core;

import com.sfjs.data.entity.JobListingElementData;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * Contains fields that can be shared between
 * entity and non-entity interview process classes
 *
 * The mapped super class annotation means these fields
 * will correspond to database columns
 *
 * @author carl
 *
 */
@MappedSuperclass
public class InterviewProcess extends JobListingElementData {

  @Getter @Setter private String stage; // : String
  @Getter @Setter private String step; // : String
  @Getter @Setter private String details; // : String
}
