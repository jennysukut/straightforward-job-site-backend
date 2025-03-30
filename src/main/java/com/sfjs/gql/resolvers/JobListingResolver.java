package com.sfjs.gql.resolvers;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.data.core.InterviewProcess;
import com.sfjs.data.core.JobListing;
import com.sfjs.data.core.Responsibility;
import com.sfjs.data.entity.JobListingEntity;
import com.sfjs.gql.svc.JobListingService;
import com.sfjs.jpa.repo.JobListingRepository;

import graphql.schema.DataFetchingEnvironment;

@Controller
@Transactional
public class JobListingResolver {

  Logger logger = Logger.getLogger(getClass().getName());

  @Autowired
  private JobListingService jobListingService;

  @Autowired
  private JobListingRepository jobListingRepository;

  @QueryMapping(name = "jobListings")
  public List<JobListing> jobListings() {
    return jobListingRepository.findAll().stream().map(entity -> {
      entity.setBusinessName(entity.getBusiness().getName());
      return entity;
    }).collect(Collectors.toList());
  }

  @QueryMapping(name = "jobListing")
  public Optional<JobListingEntity> jobListing(
    @Argument(name = "id") Long id,
    DataFetchingEnvironment environment) throws Exception {
    return jobListingRepository.findById(id).map(entity -> {
      entity.setBusinessName(entity.getBusiness().getName());
      return entity;
    });
  }

  @MutationMapping(name = "createJobListing")
  @PreAuthorize("hasRole('ROLE_BUSINESS')")
  public Long createJobListing(
      @Argument(name = "jobTitle")    String jobTitle, //?: string;
      @Argument(name = "positionType")    String positionType, //?: string;
      DataFetchingEnvironment environment) throws Exception {
    return jobListingService.createJobListing(jobTitle, positionType, environment);
  }

  @MutationMapping(name = "addJobListingDetailsStep1")
  @PreAuthorize("hasRole('ROLE_BUSINESS')")
  public Long addJobListingDetailsStep1(
      @Argument(name = "id") Long id,
      @Argument(name = "positionSummary")    String positionSummary, //?: string;
      @Argument(name = "nonNegParams")    List<String> nonNegParams, //?: Array<string>;
      DataFetchingEnvironment environment) throws Exception {
    return jobListingService.addJobListingDetailsStep1(id, positionSummary, nonNegParams, environment);
  }

  @MutationMapping(name = "addJobListingDetailsStep2")
  @PreAuthorize("hasRole('ROLE_BUSINESS')")
  public Long addJobListingDetailsStep2(
      @Argument(name = "id") Long id,
      @Argument(name = "payscaleMin") float payscaleMin,
      @Argument(name = "payscaleMax") float payscaleMax,
      @Argument(name = "payOption") String payOption,
      @Argument(name = "locationOption") String locationOption,
      @Argument(name = "idealCandidate") String idealCandidate,
      @Argument(name = "daysInOffice") String daysInOffice,
      @Argument(name = "daysRemote") String daysRemote,
      DataFetchingEnvironment environment) throws Exception {
    return jobListingService.addJobListingDetailsStep2(id, payscaleMin, payscaleMax, payOption, locationOption, idealCandidate, daysInOffice, daysRemote, environment);
  }

  @MutationMapping(name = "addJobListingDetailsStep3")
  @PreAuthorize("hasRole('ROLE_BUSINESS')")
  public Long addJobListingDetailsStep3(
      @Argument(name = "id") Long id,
      @Argument(name = "experienceLevel") List<String> experienceLevel,
      @Argument(name = "preferredSkills") List<String> preferredSkills,
      @Argument(name = "moreAboutPosition") String moreAboutPosition,
      DataFetchingEnvironment environment) throws Exception {
    return jobListingService.addJobListingDetailsStep3(id, experienceLevel, preferredSkills, moreAboutPosition, environment);
  }

  @MutationMapping(name = "addJobListingDetailsStep4")
  @PreAuthorize("hasRole('ROLE_BUSINESS')")
  public Long addJobListingDetailsStep4(
      @Argument(name = "id") Long id,
      @Argument(name = "responsibilities") List<Responsibility> responsibilities,
      @Argument(name = "perks") List<String> perks,
      DataFetchingEnvironment environment) throws Exception {
    return jobListingService.addJobListingDetailsStep4(id, responsibilities, perks, environment);
  }

  @MutationMapping(name = "addJobListingDetailsStep5")
  @PreAuthorize("hasRole('ROLE_BUSINESS')")
  public Long addJobListingDetailsStep5(
      @Argument(name = "id") Long id,
      @Argument(name = "interviewProcess") List<InterviewProcess> interviewProcess,
      DataFetchingEnvironment environment) throws Exception {
    return jobListingService.addJobListingDetailsStep5(id, interviewProcess, environment);
  }

////      @Argument(name = "businessName")    String businessName, //?: string;
//      @Argument(name = "applicationLimit")    String applicationLimit, //?: string;
//      // this job number will probably get replaced by an
//      // auto-generated id made by sending details to the server?
////      @Argument(name = "jobNumber")    Long jobNumber, //?: number;
//      @Argument(name = "positionType")    String positionType, //?: string;
//      @Argument(name = "positionSummary")    String positionSummary, //?: string;
//      @Argument(name = "nonNegParams")    List<String> nonNegParams, //?: Array<string>;
////         PayDetailsData payDetails, //?: any;
//      @Argument(name = "locationOption")    String locationOption, //?: string;
//      @Argument(name = "idealCandidate")    String idealCandidate, //?: string;
////         HybridDetailsData hybridDetails, //?: any;
//      @Argument(name = "experienceLevel")    List<String> experienceLevel, //?: any;
//      @Argument(name = "preferredSkills")    List<String> preferredSkills, //?: Array<string>;
//      @Argument(name = "moreAboutPosition")    String moreAboutPosition, //?: string;
//      @Argument(name = "responsibilities")    List<String> responsibilities, //?: any;
//      @Argument(name = "perks")    List<String> perks, //?: Array<string>;
////         List<InterviewProcessData> interviewProcess, //?: Array<any>;
//      @Argument(name = "location")    String location, //?: string;
//      @Argument(name = "country")    String country, //?: string;
//      @Argument(name = "roundNumber")    Integer roundNumber,
//      DataFetchingEnvironment environment) throws Exception {
//    return jobListingService.createJobListing(jobTitle, applicationLimit, positionType, positionSummary, nonNegParams,
//        locationOption, idealCandidate, experienceLevel, preferredSkills, moreAboutPosition, responsibilities, perks,
//        location, country, roundNumber, environment);
//  }

}