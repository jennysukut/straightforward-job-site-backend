package com.sfjs.gql.svc;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sfjs.data.entity.AccountEntity;
import com.sfjs.data.entity.BusinessEntity;
import com.sfjs.data.entity.FellowEntity;
import com.sfjs.data.entity.JobApplicationEntity;
import com.sfjs.data.entity.JobApplicationNoteEntity;
import com.sfjs.data.entity.JobListingEntity;
import com.sfjs.jpa.repo.JobApplicationNoteRepository;
import com.sfjs.jpa.repo.JobApplicationRepository;
import com.sfjs.jpa.repo.JobListingRepository;
import com.sfjs.data.api.JobApplicationData;
import com.sfjs.data.core.JobApplicationNote;
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

  @Autowired
  private JobApplicationNoteRepository jobApplicationNoteRepository;

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
//      requestBody.setObjectId(entity.getId());
      requestBody.setStatus(entity.getStatus());
      return requestBody;
    } else {
      // Existing entity not found
      throw new IllegalArgumentException("Job listing is not found: " + requestBody.getJobListingId());
    }
  }

  public Long saveJobApplicationNote(Long jobApplicationId, JobApplicationNote requestBody, DataFetchingEnvironment environment) {
    logger.info("saveJobApplicationNote: " + requestBody.getText());
    Optional<JobApplicationEntity> opt = jobApplicationRepository.findById(jobApplicationId);
    if (opt.isPresent()) {
      JobApplicationEntity jobApplication = opt.get();
      JobApplicationNoteEntity entity = new JobApplicationNoteEntity();
      entity.setApplication(jobApplication);
      entity.setBusinessNote(requestBody.isBusinessNote());
      entity.setFellowNote(requestBody.isFellowNote());
      entity.setText(requestBody.getText());
      entity = jobApplicationNoteRepository.save(entity);
      return entity.getId();
    } else {
      // Existing entity not found
      throw new IllegalArgumentException("Job application is not found: " + jobApplicationId);
    }
  }
}
