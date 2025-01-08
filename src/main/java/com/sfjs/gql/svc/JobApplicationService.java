package com.sfjs.gql.svc;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sfjs.crud.entity.AccountEntity;
import com.sfjs.crud.entity.BusinessEntity;
import com.sfjs.crud.entity.FellowEntity;
import com.sfjs.crud.entity.JobApplicationEntity;
import com.sfjs.crud.entity.JobListingEntity;
import com.sfjs.crud.repo.JobApplicationRepository;
import com.sfjs.crud.repo.JobListingRepository;
import com.sfjs.data.JobApplicationData;
import com.sfjs.security.AuthorizationService;

import graphql.schema.DataFetchingEnvironment;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class JobApplicationService {

  Logger logger = Logger.getLogger(getClass().getName());

  @Autowired
  private AuthorizationService authorizationService;

  @Autowired
  private JobListingRepository jobListingRepository;

  @Autowired
  private JobApplicationRepository jobApplicationRepository;

  static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

  static {
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
  }

  public JobApplicationData saveJobApplication(JobApplicationData requestBody, DataFetchingEnvironment environment) throws Exception {
    logger.info("saveJobApplication: " + requestBody.getMessage());
    // Create new entity
    Optional<JobListingEntity> opt = jobListingRepository.findById(requestBody.getJobListingId());
    if (opt.isPresent()) {
      JobListingEntity jobListing = opt.get();
      AccountEntity accountEntity = authorizationService.getAccount();
      FellowEntity fellowEntity = accountEntity.getFellow();
      JobApplicationEntity entity = new JobApplicationEntity();
      entity.setFellow(fellowEntity);
      entity.setJobListing(jobListing);
      entity.setMessage(requestBody.getMessage());
      entity = jobApplicationRepository.save(entity);
      requestBody.setApplicant(fellowEntity.getId());
      requestBody.setAppointments(List.of());
      BusinessEntity businessEntity = jobListing.getBusiness();
      requestBody.setBusiness(businessEntity.getName());
      requestBody.setBusinessId(businessEntity.getId());
      requestBody.setBusinessNote(List.of());
      requestBody.setDateOfApp(entity.getCreatedAt().toString());
      requestBody.setFellowNote(List.of());
      requestBody.setId(entity.getId());
      requestBody.setJobListingId(jobListing.getId());
      requestBody.setMessage(entity.getMessage());
      requestBody.setObjectId(entity.getId());
      requestBody.setStatus(entity.getStatus());
      return requestBody;
    } else {
      // Existing entity not found
      throw new IllegalArgumentException("Job listing is not found: " + requestBody.getJobListingId());
    }
  }
}
