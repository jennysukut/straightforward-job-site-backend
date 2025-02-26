package com.sfjs.gql.svc;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.data.core.InterviewAppointment;
import com.sfjs.data.entity.AccountEntity;
import com.sfjs.data.entity.BusinessEntity;
import com.sfjs.data.entity.FellowEntity;
import com.sfjs.data.entity.InterviewAppointmentEntity;
import com.sfjs.data.entity.JobApplicationEntity;
import com.sfjs.data.entity.JobApplicationNoteEntity;
import com.sfjs.data.entity.JobListingEntity;
import com.sfjs.jpa.repo.AppointmentRepository;
import com.sfjs.jpa.repo.FellowRepository;
import com.sfjs.jpa.repo.JobApplicationNoteRepository;
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
  private JobApplicationNoteRepository jobApplicationNoteRepository;

  @Autowired
  private AppointmentRepository appointmentRepository;

  @Autowired
  private JobListingRepository jobListingRepository;

  @Autowired
  private FellowRepository fellowRepository;

  public Long applyToJob(
      @Argument(name = "jobId") Long jobId,
      @Argument(name = "message") String message, // ?: string;
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

  public List<Long> keepNotes(@Argument(name = "jobApplicationId") Long jobApplicationId,
      @Argument(name = "notes") List<String> notes, DataFetchingEnvironment environment) throws Exception {

    Optional<JobApplicationEntity> optionalEntity = jobApplicationRepository.findById(jobApplicationId);
    if (optionalEntity.isEmpty()) {
      throw new IllegalArgumentException("Job application does not exist: " + jobApplicationId);
    }

    JobApplicationEntity entity = optionalEntity.get();
    AccountEntity accountEntity = authorizationService.getAccount();
    FellowEntity fellowEntity = accountEntity.getFellow();
    BusinessEntity businessEntity = accountEntity.getBusiness();

    return notes.stream().map(note -> {
      JobApplicationNoteEntity noteEntity = new JobApplicationNoteEntity();
      noteEntity.setBusinessNote(businessEntity != null);
      noteEntity.setFellowNote(fellowEntity != null);
      noteEntity.setText(note);
      noteEntity.setApplication(entity);
      noteEntity = jobApplicationNoteRepository.save(noteEntity);
      return noteEntity.getId();
    }).collect(Collectors.toList());
  }

  public List<Long> scheduleAppointments(@Argument(name = "jobApplicationId") Long jobApplicationId,
      @Argument(name = "appointments") List<InterviewAppointment> appointments, DataFetchingEnvironment environment)
      throws Exception {
    Optional<JobApplicationEntity> optionalEntity = jobApplicationRepository.findById(jobApplicationId);
    if (optionalEntity.isEmpty()) {
      throw new IllegalArgumentException("Job application does not exist: " + jobApplicationId);
    }

    JobApplicationEntity jobApplicationEntity = optionalEntity.get();

    return appointments.stream().map(appointment -> {
      InterviewAppointmentEntity entity = new InterviewAppointmentEntity();
      entity.setApplication(jobApplicationEntity);
      entity.setInterviewDateAndTime(appointment.getInterviewDateAndTime());
      entity.setNote(appointment.getNote());
      entity = appointmentRepository.save(entity);
      return entity.getId();
    }).collect(Collectors.toList());
  }

  public List<Long> saveJobListing(@Argument(name = "jobId") Long jobId, DataFetchingEnvironment environment)
      throws Exception {
    logger.info("Enter saveJobListing");

    // This should work because the mutation requires a fellow role
    AccountEntity accountEntity = authorizationService.getAccount();
    logger.info("Account: " + accountEntity.getEmail());

    FellowEntity fellowEntity = accountEntity.getFellow();
    logger.info("Fellow: " + fellowEntity.getName());

    // This should work but maybe the job listing has been deleted??
    Optional<JobListingEntity> optionalJobListing = jobListingRepository.findById(jobId);
    if (optionalJobListing.isEmpty()) {
      throw new IllegalArgumentException("Job does not exist: " + jobId);
    }
    JobListingEntity jobListingEntity = optionalJobListing.get();
    logger.info("Job Listing: " + jobListingEntity);

    Set<JobListingEntity> savedJobs = fellowEntity.getSavedJobs();
    logger.info("Saved jobs: " + savedJobs);

    savedJobs.add(jobListingEntity);
    logger.info("Saved jobs again: " + savedJobs);

    fellowEntity.setSavedJobs(savedJobs);
    fellowRepository.save(fellowEntity);

    return fellowEntity.getSavedJobs().stream().map(savedJob -> {
      return savedJob.getId();
    }).collect(Collectors.toList());
  }

}
