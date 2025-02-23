package com.sfjs.gql.svc;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.data.entity.AccountEntity;
import com.sfjs.data.entity.FellowEntity;
import com.sfjs.data.entity.JobApplicationEntity;
import com.sfjs.data.entity.JobListingEntity;
import com.sfjs.jpa.repo.JobApplicationRepository;
import com.sfjs.jpa.repo.JobListingRepository;
import com.sfjs.security.AuthorizationService;

import graphql.schema.DataFetchingEnvironment;

@Service
@Transactional
public class JobApplicationService {

  Logger logger = Logger.getLogger(getClass().getName());

  @Autowired
  private AuthorizationService authorizationService;

  @Autowired
  private JobApplicationRepository jobApplicationRepository;

  @Autowired
  private JobListingRepository jobListingRepository;

  public Long applyToJob(
      @Argument(name = "jobId") Long jobId,
      @Argument(name = "message")    String message, //?: string;
      DataFetchingEnvironment environment) throws Exception {

    // This should work because the mutation requires a fellow role
    AccountEntity accountEntity = authorizationService.getAccount();
    FellowEntity fellowEntity = accountEntity.getFellow();

    // This should work but maybe the job listing has been deleted??
    Optional<JobListingEntity> optionalJobListing = jobListingRepository.findById(jobId);
    if (optionalJobListing.isEmpty()) {
      throw new IllegalArgumentException("Job does not exist: " + jobId);
    }
    JobListingEntity jobListing = optionalJobListing.get();
    JobApplicationEntity entity = new JobApplicationEntity();
    entity.setFellow(fellowEntity);
    entity.setMessage(message);
    entity.setStatus("submitted");
    entity.setJobListing(jobListing);
    return jobApplicationRepository.save(entity).getId();
  }

}
