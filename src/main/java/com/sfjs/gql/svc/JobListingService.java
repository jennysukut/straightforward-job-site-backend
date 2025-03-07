package com.sfjs.gql.svc;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sfjs.data.entity.AccountEntity;
import com.sfjs.data.entity.BusinessEntity;
import com.sfjs.data.entity.JobListingEntity;
import com.sfjs.jpa.repo.JobListingRepository;
import com.sfjs.security.AuthorizationService;

import graphql.schema.DataFetchingEnvironment;

@Service
@Transactional
public class JobListingService {

  Logger logger = Logger.getLogger(getClass().getName());

  @Autowired
  private AuthorizationService authorizationService;

  @Autowired
  private JobListingRepository jobListingRepository;

//  @Autowired
//  private InterviewProcessRepository interviewProcessRepository;

  static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

  static {
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
  }

//  public List<JobListing> listAllJobs(){
//    logger.info("listAllJobs called...");
//
//    List<JobListingEntity> allJobEntities = jobListingRepository.findAll();
//    List<JobListing> allJobs = new ArrayList<>();
//
//    for ( JobListingEntity entity : allJobEntities ){
//      JobListing jobData = convertJobEntityToJobData(entity);
//      allJobs.add(jobData);
//    }
//
//    return allJobs;
//  }

//  private JobListing convertJobEntityToJobData(JobListingEntity entity){
//    JobListing jobData = new JobListing();
//
//    jobData.setObjectId(entity.getId());
//    jobData.setJobTitle(entity.getJobTitle());
//    jobData.setBusinessName(entity.getBusinessName());
//    jobData.setApplicationLimit(entity.getApplicationLimit());
//    // need to add numberOfApps to JobListingData object?
//    jobData.setPositionType(entity.getPositionType());
//    jobData.setNonNegParams(entity.getNonNegParams());
//    jobData.setLocationOption(entity.getLocationOption());
//
//    PayDetails payDetails = getPayDetailsData(entity);
//    jobData.setPayDetails(payDetails);
//
//    HybridDetails hybridDetails = getHybridDetailsData(entity);
//    jobData.setHybridDetails(hybridDetails);
//
//    jobData.setExperienceLevel(entity.getExperienceLevel());
//    jobData.setPreferredSkills(entity.getPreferredSkills());
//    jobData.setMoreAboutPosition(entity.getMoreAboutPosition());
//    jobData.setResponsibilities(entity.getResponsibilities());
//    jobData.setPerks(entity.getPerks());
//
//    List<InterviewProcess> interviewProcessDataList = getInterviewProcessDataList(entity);
//    jobData.setInterviewProcess(interviewProcessDataList);
//    jobData.setLocation(entity.getLocation());
//    jobData.setCountry(entity.getCountry());
//    jobData.setRoundNumber(entity.getRoundNumber());
//
//    jobData.setApplications(entity.getJobApplications() != null
//      ? entity.getJobApplications().stream().map(application -> application.getId()).toList()
//      : List.of());
//
//    return jobData;
//  }
//
//  private PayDetailsData getPayDetailsData(JobListingEntity entity){
//    PayDetailsData payDetails = new PayDetailsData();
//    payDetails.setPayScaleMin(entity.getPayScaleMin());
//    payDetails.setPayScaleMax(entity.getPayScaleMax());
//    payDetails.setPayOption(entity.getPayOption());
//
//    return payDetails;
//  }
//
//  private HybridDetailsData getHybridDetailsData(JobListingEntity entity){
//    HybridDetailsData hybridDetails = new HybridDetailsData();
//    hybridDetails.setDaysInOffice(entity.getDaysInOffice());
//    hybridDetails.setDaysRemote(entity.getDaysRemote());
//
//    return hybridDetails;
//  }
//
//  private List<InterviewProcessData> getInterviewProcessDataList(JobListingEntity entity) {
//    List<InterviewProcessEntity> interviewProcessEntities = entity.getInterviewProcess();
//    List<InterviewProcessData> interviewProcessDataList = new ArrayList<InterviewProcessData>();
//
//    for (InterviewProcessEntity interviewProcessEntity : interviewProcessEntities){
//      InterviewProcessData interviewProcessData = new InterviewProcessData();
//
//      interviewProcessData.setStage(interviewProcessEntity.getStage());
//      interviewProcessData.setStep(interviewProcessEntity.getStep());
//      interviewProcessData.setDetails(interviewProcessEntity.getDetails());
//
//      interviewProcessDataList.add(interviewProcessData);
//    }
//    return interviewProcessDataList;
//  }

  public Long createJobListing(
      @Argument(name = "name")    String name, //?: string;
//      @Argument(name = "businessName")    String businessName, //?: string;
      @Argument(name = "applicationLimit")    String applicationLimit, //?: string;
      // this job number will probably get replaced by an
      // auto-generated id made by sending details to the server?
//      @Argument(name = "jobNumber")    Long jobNumber, //?: number;
      @Argument(name = "positionType")    String positionType, //?: string;
      @Argument(name = "positionSummary")    String positionSummary, //?: string;
      @Argument(name = "nonNegParams")    List<String> nonNegParams, //?: Array<string>;
//         PayDetailsData payDetails, //?: any;
      @Argument(name = "locationOption")    String locationOption, //?: string;
      @Argument(name = "idealCandidate")    String idealCandidate, //?: string;
//         HybridDetailsData hybridDetails, //?: any;
      @Argument(name = "experienceLevel")    List<String> experienceLevel, //?: any;
      @Argument(name = "preferredSkills")    List<String> preferredSkills, //?: Array<string>;
      @Argument(name = "moreAboutPosition")    String moreAboutPosition, //?: string;
      @Argument(name = "responsibilities")    List<String> responsibilities, //?: any;
      @Argument(name = "perks")    List<String> perks, //?: Array<string>;
//         List<InterviewProcessData> interviewProcess, //?: Array<any>;
      @Argument(name = "location")    String location, //?: string;
      @Argument(name = "country")    String country, //?: string;
      @Argument(name = "roundNumber")    Integer roundNumber,
      DataFetchingEnvironment environment) throws Exception {
    // Create new entity
    AccountEntity accountEntity = authorizationService.getAccount();
    BusinessEntity businessEntity = accountEntity.getBusiness();
    JobListingEntity entity = new JobListingEntity();
    entity.setBusiness(businessEntity);

//    @Argument(name = "jobTitle")    String jobTitle, //?: string;
    entity.setReference(name);
//    @Argument(name = "businessName")    String businessName, //?: string;
//    @Argument(name = "applicationLimit")    String applicationLimit, //?: string;
    entity.setApplicationLimit(applicationLimit);
    // this job number will probably get replaced by an
    // auto-generated id made by sending details to the server?
//    @Argument(name = "jobNumber")    Long jobNumber, //?: number;
//    @Argument(name = "positionType")    String positionType, //?: string;
    entity.setPositionType(positionType);
//    @Argument(name = "positionSummary")    String positionSummary, //?: string;
    entity.setPositionSummary(positionSummary);
//    @Argument(name = "nonNegParams")    List<String> nonNegParams, //?: Array<string>;
    entity.setNonNegParams(nonNegParams);
//       PayDetailsData payDetails, //?: any;
//    @Argument(name = "locationOption")    String locationOption, //?: string;
    entity.setLocationOption(locationOption);
//    @Argument(name = "idealCandidate")    String idealCandidate, //?: string;
    entity.setIdealCandidate(idealCandidate);
//       HybridDetailsData hybridDetails, //?: any;
//    @Argument(name = "experienceLevel")    List<String> experienceLevel, //?: any;
    entity.setExperienceLevel(experienceLevel);
//    @Argument(name = "preferredSkills")    List<String> preferredSkills, //?: Array<string>;
    entity.setPreferredSkills(preferredSkills);
//    @Argument(name = "moreAboutPosition")    String moreAboutPosition, //?: string;
    entity.setMoreAboutPosition(moreAboutPosition);
//    @Argument(name = "responsibilities")    List<String> responsibilities, //?: any;
    entity.setResponsibilities(responsibilities);
//    @Argument(name = "perks")    List<String> perks, //?: Array<string>;
    entity.setPerks(perks);
//       List<InterviewProcessData> interviewProcess, //?: Array<any>;
//    @Argument(name = "location")    String location, //?: string;
//    entity.setLocation(location);
//    @Argument(name = "country")    String country, //?: string;
//    entity.setCountry(country);
//    @Argument(name = "roundNumber")    Integer roundNumber,
    entity.setRoundNumber(roundNumber);
    
    entity = jobListingRepository.save(entity);

//    BaseJobListingData data = new BaseJobListingData();
//    assignFields(entity, data);
//    requestBody.setJobNumber(entity.getId());
//    requestBody.setBusinessName(businessEntity.getName());
    return entity.getId();
  }

  public Long createJobListing(
    @Argument(name = "name")    String name, //?: string;
    @Argument(name = "positionType")    String positionType, //?: string;
    DataFetchingEnvironment environment) {

    AccountEntity accountEntity = authorizationService.getAccount();
    BusinessEntity businessEntity = accountEntity.getBusiness();
    JobListingEntity entity = new JobListingEntity();
    entity.setBusiness(businessEntity);

    entity.setReference(name);
    entity.setPositionType(positionType);

    entity = jobListingRepository.save(entity);
    return entity.getId();
  }

  public Long addJobListingDetailsStep1(
      @Argument(name = "id") Long id,
      @Argument(name = "positionSummary")    String positionSummary, //?: string;
      @Argument(name = "nonNegParams")    List<String> nonNegParams, //?: Array<string>;
      DataFetchingEnvironment environment) {
    Optional<JobListingEntity> optionalEntity = jobListingRepository.findById(id);

    if (optionalEntity.isEmpty()) {
      logger.info("No profile associated with this fellow account");
      throw new IllegalArgumentException("No profile for this fellow account");
    }
    JobListingEntity entity = optionalEntity.get();
    entity.setPositionSummary(positionSummary);
    entity.setNonNegParams(nonNegParams);
    jobListingRepository.save(entity);
    return entity.getId();
  }

//  private <E extends BaseEntity, D extends JobListingElementData> E convertJobListingElementData(D data,
//      Class<E> entityType, BaseRepository<E> repository) {
//    try {
//      String json = mapper.writeValueAsString(data);
//      E e = mapper.readValue(json, entityType);
//      e.setId(data.getObjectId());
//      if (e.getId() != null) {
//        Optional<E> opt = repository.findById(e.getId());
//        if (opt.isPresent()) {
//          return opt.get();
//        }
//      }
//      return repository.save(e);
//    } catch (Exception ex) {
//      return (E) null;
//    }
//  }
}