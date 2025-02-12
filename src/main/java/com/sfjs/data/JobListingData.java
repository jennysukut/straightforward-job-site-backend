package com.sfjs.data;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Contains the non-entity-specific declarations of fields
 * not sharable between entity and non-entity profile classes
 *
 * @author carl
 *
 */
public class JobListingData extends BaseJobListingData {

  // This is not sharable because we are flattening it
  @Getter @Setter PayDetailsData payDetails; //?: any;
//  @Getter @Setter private float payScaleMin; // : Float
//  @Getter @Setter private float payScaleMax; // : Float
//  @Getter @Setter private String payOption; // : String
  // This is not sharable because we are flattening it
  @Getter @Setter HybridDetailsData hybridDetails; //?: any;
//  @Getter @Setter private String daysInOffice; // : String
//  @Getter @Setter private String daysRemote; // : String
  @Getter @Setter List<InterviewProcessData> interviewProcess; //?: Array<any>;

  @Getter @Setter List<Long> applications; //?: Array<any>
}
