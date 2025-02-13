package com.sfjs.gql.svc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sfjs.data.entity.AccountEntity;
import com.sfjs.data.entity.BusinessEntity;
import com.sfjs.data.entity.InterviewProcessEntity;
import com.sfjs.data.entity.JobListingEntity;
import com.sfjs.jpa.repo.BaseRepository;
import com.sfjs.jpa.repo.InterviewProcessRepository;
import com.sfjs.jpa.repo.JobListingRepository;
import com.sfjs.data.BaseObject;
import com.sfjs.data.api.JobListingData;
import com.sfjs.data.api.JobListingElementData;
import com.sfjs.data.api.PayDetailsData;
import com.sfjs.data.core.HybridDetails;
import com.sfjs.data.core.InterviewProcess;
import com.sfjs.security.AuthorizationService;

import graphql.schema.DataFetchingEnvironment;
import jakarta.transaction.Transactional;

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

  public List<JobListingData> listAllJobs(){
    logger.info("listAllJobs called...");

    List<JobListingEntity> allJobEntities = jobListingRepository.findAll();
    List<JobListingData> allJobs = new ArrayList<JobListingData>();

    for ( JobListingEntity entity : allJobEntities ){
      JobListingData jobData = convertJobEntityToJobData(entity);
      allJobs.add(jobData);
    }

    return allJobs;
  }

  private JobListingData convertJobEntityToJobData(JobListingEntity entity){
    JobListingData jobData = new JobListingData();

//    jobData.setObjectId(entity.getId());
    jobData.setJobTitle(entity.getJobTitle());
    jobData.setBusinessName(entity.getBusinessName());
    jobData.setApplicationLimit(entity.getApplicationLimit());
    // need to add numberOfApps to JobListingData object?
    jobData.setPositionType(entity.getPositionType());
    jobData.setNonNegParams(entity.getNonNegParams());
    jobData.setLocationOption(entity.getLocationOption());

    PayDetailsData payDetails = getPayDetailsData(entity);
    jobData.setPayDetails(payDetails);

    HybridDetails hybridDetails = getHybridDetailsData(entity);
    jobData.setHybridDetails(hybridDetails);

    jobData.setExperienceLevel(entity.getExperienceLevel());
    jobData.setPreferredSkills(entity.getPreferredSkills());
    jobData.setMoreAboutPosition(entity.getMoreAboutPosition());
    jobData.setResponsibilities(entity.getResponsibilities());
    jobData.setPerks(entity.getPerks());

    List<InterviewProcess> interviewProcessDataList = getInterviewProcessDataList(entity);
    jobData.setInterviewProcess(interviewProcessDataList);
    jobData.setLocation(entity.getLocation());
    jobData.setCountry(entity.getCountry());
    jobData.setRoundNumber(entity.getRoundNumber());

    jobData.setApplications(entity.getJobApplications() != null
      ? entity.getJobApplications().stream().map(application -> application.getId()).toList()
      : List.of());

    return jobData;
  }

  private PayDetailsData getPayDetailsData(JobListingEntity entity){
    PayDetailsData payDetails = new PayDetailsData();
    payDetails.setPayScaleMin(entity.getPayScaleMin());
    payDetails.setPayScaleMax(entity.getPayScaleMax());
    payDetails.setPayOption(entity.getPayOption());

    return payDetails;
  }

  private HybridDetails getHybridDetailsData(JobListingEntity entity){
    HybridDetails hybridDetails = new HybridDetails();
    hybridDetails.setDaysInOffice(entity.getDaysInOffice());
    hybridDetails.setDaysRemote(entity.getDaysRemote());

    return hybridDetails;
  }

  private List<InterviewProcess> getInterviewProcessDataList(JobListingEntity entity) {
    List<InterviewProcessEntity> interviewProcessEntities = entity.getInterviewProcess();
    List<InterviewProcess> interviewProcessDataList = new ArrayList<InterviewProcess>();

    for (InterviewProcessEntity interviewProcessEntity : interviewProcessEntities){
      InterviewProcess interviewProcessData = new InterviewProcess();

      interviewProcessData.setStage(interviewProcessEntity.getStage());
      interviewProcessData.setStep(interviewProcessEntity.getStep());
      interviewProcessData.setDetails(interviewProcessEntity.getDetails());

      interviewProcessDataList.add(interviewProcessData);
    }
    return interviewProcessDataList;
  }

  public JobListingData saveJobListing(JobListingData requestBody, DataFetchingEnvironment environment) throws Exception {
    if (requestBody.getJobNumber() != null) {
      Optional<JobListingEntity> opt = jobListingRepository.findById(requestBody.getJobNumber());
      if (opt.isPresent()) {
        // Update existing entity
        JobListingEntity entity = opt.get();
        assignFields(requestBody, entity);
        entity = jobListingRepository.save(entity);
//        BaseJobListingData data = new BaseJobListingData();
//        assignFields(entity, data);
        requestBody.setJobNumber(entity.getId());
        return requestBody;
      } else {
        // Existing entity not found
        throw new IllegalArgumentException("Job listing is not found: " + requestBody.getJobNumber());
      }
    }
    // Create new entity
    AccountEntity accountEntity = authorizationService.getAccount();
    BusinessEntity businessEntity = accountEntity.getBusiness();
    JobListingEntity entity = new JobListingEntity();
    entity.setBusiness(businessEntity);
    assignFields(requestBody, entity);
    entity = jobListingRepository.save(entity);
//    BaseJobListingData data = new BaseJobListingData();
//    assignFields(entity, data);
    requestBody.setJobNumber(entity.getId());
    requestBody.setBusinessName(businessEntity.getName());
    return requestBody;
  }

  private void assignFields(JobListingData in, JobListingEntity out) {
    // base fields
    out.setApplicationLimit(in.getApplicationLimit());
    out.setCountry(in.getCountry());
    out.setIdealCandidate(in.getIdealCandidate());
    out.setJobTitle(in.getJobTitle());
    out.setLocation(in.getLocation());
    out.setLocationOption(in.getLocationOption());
    out.setMoreAboutPosition(in.getMoreAboutPosition());
    out.setNonNegParams(in.getNonNegParams());
    out.setPerks(in.getPerks());
    out.setPositionSummary(in.getPositionSummary());
    out.setPositionType(in.getPositionType());
    out.setPreferredSkills(in.getPreferredSkills());
    // extended fields
    // flatten pay details
    out.setPayOption(in.getPayDetails().getPayOption());
    out.setPayScaleMax(in.getPayDetails().getPayScaleMax());
    out.setPayScaleMin(in.getPayDetails().getPayScaleMin());
    // flatten hybrid details
    out.setDaysInOffice(in.getHybridDetails().getDaysInOffice());
    out.setDaysRemote(in.getHybridDetails().getDaysRemote());
    // interview process
    if (in.getInterviewProcess() != null) {
      out.setInterviewProcess(in.getInterviewProcess().stream().map(data -> {
        InterviewProcessEntity e = this.convertJobListingElementData(data, InterviewProcessEntity.class, interviewProcessRepository);
        e.setJobListing(out);
        interviewProcessRepository.save(e);
        return e;
      }).collect(Collectors.toList()));
    }
  }

  private <E extends BaseObject, D extends JobListingElementData>
  E convertJobListingElementData(D data, Class<E> entityType,
    BaseRepository<E> repository) {
  try {
    String json = mapper.writeValueAsString(data);
    E e = mapper.readValue(json, entityType);
//    e.setId(data.getObjectId());
    if (e.getId() != null) {
      Optional<E> opt = repository.findById(e.getId());
      if (opt.isPresent()) {
        return opt.get();
      }
    }
    return repository.save(e);
  } catch (Exception ex) {
    return (E)null;
  }
}
}
