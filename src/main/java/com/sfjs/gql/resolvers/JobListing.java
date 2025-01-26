package com.sfjs.gql.resolvers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.data.JobListingData;
import com.sfjs.gql.svc.JobListingService;

import graphql.schema.DataFetchingEnvironment;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
@Transactional
public class JobListing {

  Logger logger = Logger.getLogger(getClass().getName());

  @Autowired
  private JobListingService jobListingService;

  @QueryMapping(name = "listAllJobs")
  public List<JobListingData> listAllJobs(){

    List<JobListingData> allJobs = jobListingService.listAllJobs();

    logger.info("listAllJobs query executed...");


    return allJobs;
  }

  @MutationMapping(name = "saveJobListing")
  public void saveJobListing(@Argument(name = "requestBody") JobListingData requestBody,
      DataFetchingEnvironment environment) throws Exception {
    System.out.println("saveJobListing");
    jobListingService.saveJobListing(requestBody, environment);
    return;
  }

}
