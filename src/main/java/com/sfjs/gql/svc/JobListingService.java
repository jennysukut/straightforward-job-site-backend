package com.sfjs.gql.svc;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.crud.entity.AccountEntity;
import com.sfjs.crud.entity.BusinessEntity;
import com.sfjs.crud.entity.JobListingEntity;
import com.sfjs.crud.repo.JobListingRepository;
import com.sfjs.data.JobListingData;
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

  public JobListingData saveJobListing(JobListingData requestBody, DataFetchingEnvironment environment) throws Exception {
    if (requestBody.getJobNumber() != null) {
      Optional<JobListingEntity> opt = jobListingRepository.findById(requestBody.getJobNumber());
      if (opt.isPresent()) {
        // Update existing entity
        JobListingEntity entity = opt.get();
        assignFields(requestBody, entity);
        entity = jobListingRepository.save(entity);
        JobListingData data = new JobListingData();
        assignFields(entity, data);
        data.setJobNumber(entity.getId());
        return data;
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
    JobListingData data = new JobListingData();
    assignFields(entity, data);
    data.setJobNumber(entity.getId());
    data.setBusinessName(businessEntity.getName());
    return data;
  }

  private void assignFields(JobListingData in, JobListingData out) {
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
  }
}
