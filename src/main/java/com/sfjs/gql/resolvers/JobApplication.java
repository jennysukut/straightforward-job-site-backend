package com.sfjs.gql.resolvers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.data.JobApplicationData;
import com.sfjs.gql.svc.JobApplicationService;

import graphql.schema.DataFetchingEnvironment;

@Controller
@Transactional
public class JobApplication {

  @Autowired
  private JobApplicationService jobApplicationService;

  @MutationMapping(name = "saveJobApplication")
  public JobApplicationData saveJobApplication(@Argument(name = "requestBody") JobApplicationData requestBody,
      DataFetchingEnvironment environment) throws Exception {
    System.out.println("saveJobApplication");
    return jobApplicationService.saveJobApplication(requestBody, environment);
  }

}
