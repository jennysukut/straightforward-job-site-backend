package com.sfjs.gql.resolvers;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.data.core.JobInterviewProcessStepAppointment;
import com.sfjs.data.entity.JobApplicationEntity;
import com.sfjs.data.entity.JobApplicationNoteEntity;
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

  @MutationMapping(name = "editNote")
  @PreAuthorize("hasAnyRole('ROLE_FELLOW', 'ROLE_BUSINESS')")
  public Optional<JobApplicationNoteEntity> editNote(
      @Argument(name = "noteId") Long noteId,
      @Argument(name = "note") String note,
      DataFetchingEnvironment environment) throws Exception {
    return jobApplicationService.editNote(noteId, note, environment);
  }

  @MutationMapping(name = "deleteNote")
  @PreAuthorize("hasAnyRole('ROLE_FELLOW', 'ROLE_BUSINESS')")
  public Boolean deleteNote(
      @Argument(name = "noteId") Long noteId,
      DataFetchingEnvironment environment) throws Exception {
    return jobApplicationService.deleteNote(noteId, environment);
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

  @QueryMapping(name = "getApplication")
  public Optional<JobApplicationEntity> getApplication(
    @Argument(name = "id") Long id,
    DataFetchingEnvironment environment) throws Exception {
    logger.info("Enter getApplication");
    return jobApplicationService.getApplication(id, environment);
  }

  @MutationMapping(name = "rejectApp")
  public Optional<JobApplicationEntity> rejectApp(
      @Argument(name = "appId") Long appId,
      @Argument(name = "rejectionMessage") String rejectionMessage,
      @Argument(name = "rejectionDetails") String rejectionDetails,
      DataFetchingEnvironment environment) throws Exception {
    logger.info("Enter rejectApp");
    return jobApplicationService.rejectApp(appId, rejectionMessage, rejectionDetails, environment);
  }

  @MutationMapping(name = "highlightApp")
  public Optional<JobApplicationEntity> highlightApp(
      @Argument(name = "appId") Long appId,
      DataFetchingEnvironment environment) throws Exception {
    logger.info("Enter highlightApp");
    return jobApplicationService.highlightApp(appId, environment);
  }

  @MutationMapping(name = "updateStatus")
  public Optional<JobApplicationEntity> updateStatus(
      @Argument(name = "appId") Long appId,
      @Argument(name = "status") String status,
      DataFetchingEnvironment environment) throws Exception {
    logger.info("Enter updateStatus");
    return jobApplicationService.updateStatus(appId, status, environment);
  }

  @MutationMapping(name = "sendJobOffer")
  public Optional<JobApplicationEntity> sendJobOffer(
      @Argument(name = "appId") Long appId,
      DataFetchingEnvironment environment) throws Exception {
    logger.info("Enter sendJobOffer");
    return jobApplicationService.sendJobOffer(appId, environment);
  }
}
