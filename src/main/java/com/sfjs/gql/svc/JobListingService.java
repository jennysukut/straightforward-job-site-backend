package com.sfjs.gql.svc;

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
    AccountEntity accountEntity = authorizationService.getAccount();
    BusinessEntity businessEntity = accountEntity.getBusiness();
    JobListingEntity entity = new JobListingEntity();
    entity.setBusiness(businessEntity);
    // populate all fields
    jobListingRepository.save(entity);
    JobListingData data = new JobListingData();
    data.setJobNumber(entity.getId());
    // populate all fields
    return data;
  }
}
