package com.sfjs.gql.svc;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sfjs.data.core.InterviewProcess;
import com.sfjs.data.entity.BusinessEntity;
import com.sfjs.data.entity.FellowEntity;
import com.sfjs.data.entity.InterviewProcessEntity;
import com.sfjs.data.entity.JobListingEntity;
import com.sfjs.jpa.repo.InterviewProcessRepository;
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

  @Autowired
  private InterviewProcessRepository interviewProcessRepository;

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
    @Argument(name = "jobTitle")    String jobTitle, //?: string;
    @Argument(name = "positionType")    String positionType, //?: string;
    @Argument(name = "beingEdited") Optional<Boolean> beingEdited,
    @Argument(name = "published") Optional<Boolean> published,
    @Argument(name = "completed") Optional<String> completed,
    DataFetchingEnvironment environment) throws Exception {

    return authorizationService.getAccount().map(accountEntity -> {
      BusinessEntity businessEntity = accountEntity.getBusiness();
      JobListingEntity entity = new JobListingEntity();
      entity.setBusiness(businessEntity);
      entity.setJobTitle(jobTitle);
      entity.setPositionType(positionType);
      if (beingEdited.isPresent()) {
        entity.setBeingEdited(beingEdited.get());
      }
      if (published.isPresent()) {
        entity.setPublished(published.get());
      }
      if (completed.isPresent()) {
        entity.setCompleted(completed.get());
      }
      entity = jobListingRepository.save(entity);
      return entity.getId();
    }).orElseThrow(() -> {
      return new IllegalArgumentException("Business is not logged in");
    });
  }

  public Long addJobListingDetailsStep1(
      @Argument(name = "id") Long id,
      @Argument(name = "positionSummary")    String positionSummary, //?: string;
      @Argument(name = "nonNegParams")    List<String> nonNegParams, //?: Array<string>;
      @Argument(name = "completed") Optional<String> completed,
      DataFetchingEnvironment environment) {
    Optional<JobListingEntity> optionalEntity = jobListingRepository.findById(id);

    if (optionalEntity.isEmpty()) {
      logger.info("No job listing with this id: " + id);
      throw new IllegalArgumentException("No job listing with this id: " + id);
    }
    JobListingEntity entity = optionalEntity.get();
    entity.setPositionSummary(positionSummary);
    entity.setNonNegParams(nonNegParams);
    if (completed.isPresent()) {
      entity.setCompleted(completed.get());
    }
    jobListingRepository.save(entity);
    return entity.getId();
  }

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
    Optional<JobListingEntity> optionalEntity = jobListingRepository.findById(id);

    if (optionalEntity.isEmpty()) {
      logger.info("No job listing with this id: " + id);
      throw new IllegalArgumentException("No job listing with this id: " + id);
    }
    JobListingEntity entity = optionalEntity.get();
    entity.setPayscaleMin(payscaleMin);
    entity.setPayscaleMax(payscaleMax);
    entity.setPayOption(payOption);
    entity.setLocationOption(locationOption);
    entity.setIdealCandidate(idealCandidate);
    entity.setDaysInOffice(daysInOffice);
    entity.setDaysRemote(daysRemote);
    entity.setCity(city);
    entity.setState(state);
    if (completed.isPresent()) {
      entity.setCompleted(completed.get());
    }
    jobListingRepository.save(entity);
    return entity.getId();
  }

  public Long addJobListingDetailsStep3(
      @Argument(name = "id") Long id,
      @Argument(name = "experienceLevel") List<String> experienceLevel,
      @Argument(name = "preferredSkills") List<String> preferredSkills,
      @Argument(name = "moreAboutPosition") String moreAboutPosition,
      @Argument(name = "completed") Optional<String> completed,
      DataFetchingEnvironment environment) {
    Optional<JobListingEntity> optionalEntity = jobListingRepository.findById(id);

    if (optionalEntity.isEmpty()) {
      logger.info("No job listing with this id: " + id);
      throw new IllegalArgumentException("No job listing with this id: " + id);
    }
    JobListingEntity entity = optionalEntity.get();
    entity.setExperienceLevel(experienceLevel);
    entity.setPreferredSkills(preferredSkills);
    entity.setMoreAboutPosition(moreAboutPosition);
    if (completed.isPresent()) {
      entity.setCompleted(completed.get());
    }
    jobListingRepository.save(entity);
    return entity.getId();
  }

  public Long addJobListingDetailsStep4(
      @Argument(name = "id") Long id,
      @Argument(name = "responsibilities") List<String> responsibilities,
      @Argument(name = "perks") List<String> perks,
      @Argument(name = "completed") Optional<String> completed,
      DataFetchingEnvironment environment) {
    Optional<JobListingEntity> optionalEntity = jobListingRepository.findById(id);

    if (optionalEntity.isEmpty()) {
      logger.info("No job listing with this id: " + id);
      throw new IllegalArgumentException("No job listing with this id: " + id);
    }
    JobListingEntity entity = optionalEntity.get();
    entity.setResponsibilities(responsibilities);
    entity.setPerks(perks);
    if (completed.isPresent()) {
      entity.setCompleted(completed.get());
    }
    jobListingRepository.save(entity);
    return entity.getId();
  }

  public Long addJobListingDetailsStep5(
      @Argument(name = "id") Long id,
      @Argument(name = "interviewProcess") List<InterviewProcess> interviewProcess,
      @Argument(name = "completed") Optional<String> completed,
      DataFetchingEnvironment environment) {
    Optional<JobListingEntity> optionalEntity = jobListingRepository.findById(id);

    if (optionalEntity.isEmpty()) {
      logger.info("No job listing with this id: " + id);
      throw new IllegalArgumentException("No job listing with this id: " + id);
    }
    JobListingEntity jobListingEntity = optionalEntity.get();
    jobListingEntity.setInterviewProcess(interviewProcess.stream().map(data -> {
      InterviewProcessEntity entity = new InterviewProcessEntity();
      entity.setStage(data.getStage());
      entity.setStep(data.getStep());
      entity.setDetails(data.getDetails());
      entity.setJobListing(jobListingEntity);
      entity = interviewProcessRepository.save(entity);
      return entity;
    }).collect(Collectors.toList()));
    if (completed.isPresent()) {
      jobListingEntity.setCompleted(completed.get());
    }
    jobListingRepository.save(jobListingEntity);
    return jobListingEntity.getId();
  }

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

    AtomicReference<Specification<JobListingEntity>> specRef = getSearchSpec(businessId, isSaved, experienceLevel,
        locationOption, positionType, country, isPublished, searchbar, location, environment);

    return authorizationService.getAccount()
      .map(accountEntity -> accountEntity.getFellow())
      .map(fellowEntity -> {
        isSaved.ifPresent(value -> {
          adjustSearchCriteria(specRef, fellowEntity, value);
        });
        // Execute the query with the final specification
        List<JobListingEntity> result = jobListingRepository.findAll(specRef.get());
        // And then map and return the results
        return result.stream().map(jobListing -> {
          jobListing.setSaved(jobListing.getFellows().stream().anyMatch(fellow -> {
            return fellow.getId() == fellowEntity.getId();
          }));
          return jobListing;
        }).collect(Collectors.toList());
      }).orElseGet(() -> {
        // Execute the query with the final specification
        return jobListingRepository.findAll(specRef.get());
      });
    }

  private void adjustSearchCriteria(
    AtomicReference<Specification<JobListingEntity>> specRef,
    FellowEntity fellowEntity,
    Boolean isSavedValue) {
    if (isSavedValue) {
      specRef.set(specRef.get()
          .and((root, query, criteriaBuilder) -> criteriaBuilder.isMember(fellowEntity, root.get("fellows"))));
    } else {
      specRef.set(specRef.get()
          .and((root, query, criteriaBuilder) -> criteriaBuilder.isNotMember(fellowEntity, root.get("fellows"))));
    }
  }

  private AtomicReference<Specification<JobListingEntity>> getSearchSpec(
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

    // Use AtomicReference to hold the Specification
    AtomicReference<Specification<JobListingEntity>> specRef = new AtomicReference<>(Specification.where(null));

    specRef.set(specRef.get()
        .and((root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("deletedAt"))));

    if (isPublished.isPresent()) {
      specRef.set(specRef.get()
          .and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("published"), isPublished.get())));
    }

    // Add filters dynamically
    // Filter by businessId
    businessId.ifPresent(businessIdValue -> {
      specRef.set(specRef.get().and(
          (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("business").get("id"), businessIdValue)));
    });

    // Filter by experienceLevel (CSV matching using LIKE)
    experienceLevel.ifPresent(levels -> {
      specRef.set(specRef.get()
          .and((root, query, criteriaBuilder) -> levels.stream()
              .map(level -> criteriaBuilder.like(root.get("experienceLevel").as(String.class), "%" + level + "%"))
              .reduce(criteriaBuilder::or).orElse(null)));
    });

    // Filter by locationOption
    locationOption.ifPresent(locationOptions -> {
      specRef.set(specRef.get().and((root, query, criteriaBuilder) -> root.get("locationOption").in(locationOptions)));
    });

    // Filter by positionType
    positionType.ifPresent(positionTypes -> {
      specRef.set(specRef.get().and((root, query, criteriaBuilder) -> root.get("positionType").in(positionTypes)));
    });

    // Filter by country
    country.ifPresent(countryValue -> {
      specRef.set(specRef.get()
          .and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("business").get("businessProfile").get("country"), countryValue)));
    });

    // Filter by searchbar
    searchbar.ifPresent(searchbarValue -> {
        specRef.set(specRef.get().and((root, query, criteriaBuilder) ->
            criteriaBuilder.or(
                criteriaBuilder.like(root.get("jobTitle").as(String.class), "%" + searchbarValue + "%"),
                criteriaBuilder.like(root.get("nonNegParams").as(String.class), "%" + searchbarValue + "%"),
                criteriaBuilder.like(root.get("preferredSkills").as(String.class), "%" + searchbarValue + "%")
            )
        ));
    });

    // Filter by location
    location.ifPresent(locationValue -> {
        specRef.set(specRef.get().and((root, query, criteriaBuilder) ->
            criteriaBuilder.or(
                criteriaBuilder.like(root.get("city").as(String.class), "%" + locationValue + "%"),
                criteriaBuilder.like(root.get("state").as(String.class), "%" + locationValue + "%"),
                criteriaBuilder.like(root.get("business").get("businessProfile").get("location").as(String.class), "%" + locationValue + "%")
            )
        ));
    });

    return specRef;
  }

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

    AtomicReference<Specification<JobListingEntity>> specRef = getSearchSpec(businessId, isSaved, experienceLevel,
        locationOption, positionType, country, isPublished, searchbar, location, environment);

    Pageable request = PageRequest.of(pageNumber, pageSize);

    return authorizationService.getAccount()
      .map(accountEntity -> accountEntity.getFellow())
      .map(fellowEntity -> {
        isSaved.ifPresent(value -> {
          adjustSearchCriteria(specRef, fellowEntity, value);
        });
        // Execute the query with the final specification
        Page<JobListingEntity> result = jobListingRepository.findAll(specRef.get(), request);
        // And then map and return the results
        return result.map(jobListing -> {
          jobListing.setSaved(jobListing.getFellows().stream().anyMatch(fellow -> {
            return fellow.getId() == fellowEntity.getId();
          }));
          return jobListing;
        });
      }).orElseGet(() -> {
        // Execute the query with the final specification
        return jobListingRepository.findAll(specRef.get(), request);
      });

  }

  public Optional<Boolean> starOrStopEditingJobListing(
    @Argument(name = "id") Long id,
    @Argument(name = "beingEdited") boolean beingEdited,
    @Argument(name = "completed") Optional<String> completed,
    DataFetchingEnvironment environment) throws Exception {
    return jobListingRepository.findById(id).map(jobListingEntity -> {
      jobListingEntity.setBeingEdited(beingEdited);
      if (completed.isPresent()) {
        jobListingEntity.setCompleted(completed.get());
      }
      return jobListingRepository.save(jobListingEntity).isBeingEdited();
    });
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