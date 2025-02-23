package com.sfjs.gql.resolvers;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;

import com.sfjs.gql.svc.JobApplicationService;

import graphql.schema.DataFetchingEnvironment;

public class JobApplicationResolver {

  Logger logger = Logger.getLogger(getClass().getName());

  @Autowired
  private JobApplicationService jobApplicationService;

  @MutationMapping(name = "applyToJob")
  @PreAuthorize("hasRole('ROLE_FELLOW')")
  public Long applyToJob(
      @Argument(name = "jobId") Long jobId,
      @Argument(name = "message")    String message, //?: string;
      DataFetchingEnvironment environment) throws Exception {
    return jobApplicationService.applyToJob(jobId, message, environment);
  }
}
