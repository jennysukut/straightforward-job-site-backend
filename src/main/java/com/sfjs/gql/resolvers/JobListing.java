package com.sfjs.gql.resolvers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.data.JobListingData;
import com.sfjs.gql.svc.JobListingService;

import graphql.schema.DataFetchingEnvironment;

@Controller
@Transactional
public class JobListing {

  @Autowired
  private JobListingService jobListingService;

  @MutationMapping(name = "saveJobListing")
  public void saveJobListing(@Argument(name = "requestBody") JobListingData requestBody,
      DataFetchingEnvironment environment) throws Exception {
    System.out.println("saveJobListing");
    jobListingService.saveJobListing(requestBody, environment);
    return;
  }

}
