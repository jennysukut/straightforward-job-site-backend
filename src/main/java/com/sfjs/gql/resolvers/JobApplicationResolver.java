package com.sfjs.gql.resolvers;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.data.core.JobInterviewProcessStepAppointment;
import com.sfjs.gql.svc.JobApplicationService;

import graphql.schema.DataFetchingEnvironment;

@Controller
@Transactional
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

  @MutationMapping(name = "keepNotes")
  @PreAuthorize("hasAnyRole('ROLE_FELLOW', 'ROLE_BUSINESS')")
  public List<Long> keepNotes(
      @Argument(name = "jobApplicationId") Long jobApplicationId,
      @Argument(name = "notes")    List<String> notes,
      DataFetchingEnvironment environment) throws Exception {
    return jobApplicationService.keepNotes(jobApplicationId, notes, environment);
  }

  @MutationMapping(name = "scheduleAppointments")
  @PreAuthorize("hasRole('ROLE_BUSINESS')")
  public List<Long> scheduleAppointments(
    @Argument(name = "jobApplicationId") Long jobApplicationId,
    @Argument(name = "appointments")    List<JobInterviewProcessStepAppointment> appointments,
    DataFetchingEnvironment environment) throws Exception {
    return jobApplicationService.scheduleAppointments(jobApplicationId, appointments, environment);
  }

  @MutationMapping(name = "saveJobListing")
  @PreAuthorize("hasRole('ROLE_FELLOW')")
  public List<Long> saveJobListing(
    @Argument(name = "jobId") Long jobId,
    DataFetchingEnvironment environment) throws Exception {
    logger.info("Enter saveJobListing");
    return jobApplicationService.saveJobListing(jobId, environment);
  }

}
