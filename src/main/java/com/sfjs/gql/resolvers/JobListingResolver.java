package com.sfjs.gql.resolvers;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.data.core.InterviewProcess;
import com.sfjs.data.core.JobListing;
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
  public List<JobListingEntity> jobListings(
      @Argument(name = "businessId") Optional<Long> businessId,
      @Argument(name = "isSaved") Optional<Boolean> isSaved,
      @Argument(name = "experienceLevel") Optional<List<String>> experienceLevel,
      @Argument(name = "locationOption") Optional<List<String>> locationOption,
      @Argument(name = "positionType") Optional<List<String>> positionType,
      @Argument(name = "country") Optional<String> country,
      @Argument(name = "isPublished") Optional<Boolean> isPublished,
      @Argument(name = "searchbar") Optional<String> searchbar,
      @Argument(name = "location") Optional<String> location,
      DataFetchingEnvironment environment) throws Exception {

    logger.info("jobListings");
    logger.info("businessId: " + businessId);
    logger.info("isSaved: " + isSaved);
    logger.info("experienceLevel: " + businessId);
    logger.info("locationOption: " + locationOption);
    logger.info("positionType: " + positionType);
    logger.info("country: " + country);
    logger.info("isPublished: " + isPublished);
    logger.info("searchbar: " + searchbar);
    logger.info("location: " + location);

    return jobListingService.jobListings(businessId, isSaved, experienceLevel, locationOption, positionType, country, isPublished, searchbar, location, environment);
  }

  @QueryMapping(name = "jobListing")
  public Optional<JobListingEntity> jobListing(
    @Argument(name = "id") Long id,
    DataFetchingEnvironment environment) throws Exception {
    return jobListingRepository.findById(id);
  }

  @QueryMapping(name = "jobListingsPage")
  public Page<JobListingEntity> jobListingsPage(
      @Argument(name = "businessId") Optional<Long> businessId,
      @Argument(name = "isSaved") Optional<Boolean> isSaved,
      @Argument(name = "experienceLevel") Optional<List<String>> experienceLevel,
      @Argument(name = "locationOption") Optional<List<String>> locationOption,
      @Argument(name = "positionType") Optional<List<String>> positionType,
      @Argument(name = "country") Optional<String> country,
      @Argument(name = "pageNumber") Integer pageNumber,
      @Argument(name = "pageSize") Integer pageSize,
      @Argument(name = "isPublished") Optional<Boolean> isPublished,
      @Argument(name = "searchbar") Optional<String> searchbar,
      @Argument(name = "location") Optional<String> location,
      DataFetchingEnvironment environment) throws Exception {

    logger.info("jobListingsPage");
    logger.info("businessId: " + businessId);
    logger.info("isSaved: " + isSaved);
    logger.info("experienceLevel: " + businessId);
    logger.info("locationOption: " + locationOption);
    logger.info("positionType: " + positionType);
    logger.info("country: " + country);
    logger.info("isPublished: " + isPublished);
    logger.info("searchbar: " + searchbar);
    logger.info("location: " + location);

    return jobListingService.jobListingsPage(businessId, isSaved, experienceLevel, locationOption, positionType, country,
        pageNumber, pageSize, isPublished, searchbar, location, environment);
  }

  @MutationMapping(name = "createJobListing")
  @PreAuthorize("hasRole('ROLE_BUSINESS')")
  public Long createJobListing(
      @Argument(name = "jobTitle")    String jobTitle, //?: string;
      @Argument(name = "positionType")    String positionType, //?: string;
      @Argument(name = "beingEdited") Optional<Boolean> beingEdited,
      @Argument(name = "published") Optional<Boolean> published,
      @Argument(name = "completed") Optional<String> completed,
      DataFetchingEnvironment environment) throws Exception {
    return jobListingService.createJobListing(jobTitle, positionType, beingEdited,
        published, completed, environment);
  }

  @MutationMapping(name = "addJobListingDetailsStep1")
  @PreAuthorize("hasRole('ROLE_BUSINESS')")
  public Long addJobListingDetailsStep1(
      @Argument(name = "id") Long id,
      @Argument(name = "positionSummary")    String positionSummary, //?: string;
      @Argument(name = "nonNegParams")    List<String> nonNegParams, //?: Array<string>;
      @Argument(name = "completed") Optional<String> completed,
      DataFetchingEnvironment environment) throws Exception {
    return jobListingService.addJobListingDetailsStep1(id, positionSummary, nonNegParams, completed, environment);
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
      @Argument(name = "city") String city,
      @Argument(name = "state") String state,
      @Argument(name = "completed") Optional<String> completed,
      DataFetchingEnvironment environment) throws Exception {
    return jobListingService.addJobListingDetailsStep2(id, payscaleMin, payscaleMax, payOption, locationOption,
        idealCandidate, daysInOffice, daysRemote, city, state, completed, environment);
  }

  @MutationMapping(name = "addJobListingDetailsStep3")
  @PreAuthorize("hasRole('ROLE_BUSINESS')")
  public Long addJobListingDetailsStep3(
      @Argument(name = "id") Long id,
      @Argument(name = "experienceLevel") List<String> experienceLevel,
      @Argument(name = "preferredSkills") List<String> preferredSkills,
      @Argument(name = "moreAboutPosition") String moreAboutPosition,
      @Argument(name = "completed") Optional<String> completed,
      DataFetchingEnvironment environment) throws Exception {
    return jobListingService.addJobListingDetailsStep3(id, experienceLevel, preferredSkills, moreAboutPosition, completed, environment);
  }

  @MutationMapping(name = "addJobListingDetailsStep4")
  @PreAuthorize("hasRole('ROLE_BUSINESS')")
  public Long addJobListingDetailsStep4(
      @Argument(name = "id") Long id,
      @Argument(name = "responsibilities") List<String> responsibilities,
      @Argument(name = "perks") List<String> perks,
      @Argument(name = "completed") Optional<String> completed,
      DataFetchingEnvironment environment) throws Exception {
    return jobListingService.addJobListingDetailsStep4(id, responsibilities, perks, completed, environment);
  }

  @MutationMapping(name = "addJobListingDetailsStep5")
  @PreAuthorize("hasRole('ROLE_BUSINESS')")
  public Long addJobListingDetailsStep5(
      @Argument(name = "id") Long id,
      @Argument(name = "interviewProcess") List<InterviewProcess> interviewProcess,
      @Argument(name = "completed") Optional<String> completed,
      DataFetchingEnvironment environment) throws Exception {
    return jobListingService.addJobListingDetailsStep5(id, interviewProcess, completed, environment);
  }

  @MutationMapping(name = "createJobListingRound")
  @PreAuthorize("hasRole('ROLE_BUSINESS')")
  public Optional<JobListing> createJobListingRound(
      @Argument(name = "id") Long id,
      @Argument(name = "applicationLimit") Integer applicationLimit,
      @Argument(name = "roundNumber") Integer roundNumber,
      @Argument(name = "completed") Optional<String> completed,
      DataFetchingEnvironment environment) throws Exception {
    return jobListingRepository.findById(id).map(entity -> {
      entity.setApplicationLimit(applicationLimit);
      entity.setRoundNumber(roundNumber);
      if (completed.isPresent()) {
        entity.setCompleted(completed.get());
      }
      return jobListingRepository.save(entity);
    });
  }

  @MutationMapping(name = "starOrStopEditingJobListing")
  @PreAuthorize("hasRole('ROLE_BUSINESS')")
  public Optional<Boolean> starOrStopEditingJobListing(
    @Argument(name = "id") Long id,
    @Argument(name = "beingEdited") boolean beingEdited,
    @Argument(name = "completed") Optional<String> completed,
    DataFetchingEnvironment environment) throws Exception {
    return jobListingService.starOrStopEditingJobListing(id, beingEdited, completed, environment);
  }

  @MutationMapping(name = "publishJobListing")
  @PreAuthorize("hasRole('ROLE_BUSINESS')")
  public Optional<Long> publishJobListinging(
    @Argument(name = "id") Long id,
    @Argument(name = "completed") Optional<String> completed,
    DataFetchingEnvironment environment) throws Exception {
    return jobListingRepository.findById(id).map(entity -> {
      entity.setPublished(true);
      entity.setPublishedAt(OffsetDateTime.now());
      if (completed.isPresent()) {
        entity.setCompleted(completed.get());
      }
      return jobListingRepository.save(entity).getId();
    });
  }

  @MutationMapping(name = "deleteJobListing")
  @PreAuthorize("hasRole('ROLE_BUSINESS')")
  public Boolean deleteJobListinging(
    @Argument(name = "id") Long id,
    DataFetchingEnvironment environment) throws Exception {
    return jobListingRepository.findById(id).map(jobListingEntity -> {
      jobListingRepository.deleteById(id);
      return true;
    }).orElse(false);
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