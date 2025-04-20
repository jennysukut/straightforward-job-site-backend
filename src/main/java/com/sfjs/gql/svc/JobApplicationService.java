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

import com.sfjs.data.core.JobApplicationStatus;
import com.sfjs.data.core.JobInterviewProcessStepAppointment;
import com.sfjs.data.entity.BusinessEntity;
import com.sfjs.data.entity.ConversationEntity;
import com.sfjs.data.entity.FellowEntity;
import com.sfjs.data.entity.InterviewAppointmentEntity;
import com.sfjs.data.entity.JobApplicationEntity;
import com.sfjs.data.entity.JobApplicationNoteEntity;
import com.sfjs.data.entity.JobListingEntity;
import com.sfjs.jpa.repo.AppointmentRepository;
import com.sfjs.jpa.repo.ConversationRepository;
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

  @Autowired
  private ConversationRepository conversationRepository;

  public Long applyToJob(
      @Argument(name = "jobId") Long jobId,
      @Argument(name = "message") String message, // ?: string;
      DataFetchingEnvironment environment) throws Exception {

    return jobListingRepository.findById(jobId)
      .map(jobListing -> {
        return authorizationService.getAccount().map(accountEntity -> {
          FellowEntity fellowEntity = accountEntity.getFellow();
          JobApplicationEntity entity = new JobApplicationEntity();
          entity.setFellow(fellowEntity);
          entity.setMessage(message);
          entity.setStatus("submitted");
          entity.setJobListing(jobListing);
          ConversationEntity conversation = new ConversationEntity();
          conversation = conversationRepository.save(conversation);
          entity.setConversation(conversation);
          return jobApplicationRepository.save(entity).getId();
        }).orElseThrow(() -> {
          return new IllegalArgumentException("Fellow is not logged in");
        });
      }).orElseThrow(() -> {
        return new IllegalArgumentException("Job does not exist: " + jobId);
      });
  }

  public List<Long> keepNotes(@Argument(name = "jobApplicationId") Long jobApplicationId,
      @Argument(name = "notes") List<String> notes, DataFetchingEnvironment environment) throws Exception {

    return jobApplicationRepository.findById(jobApplicationId)
      .map(entity -> {
        return authorizationService.getAccount()
          .map(accountEntity -> {
            FellowEntity fellowEntity = accountEntity.getFellow();
            BusinessEntity businessEntity = accountEntity.getBusiness();

            return notes.stream().map(note -> {
              JobApplicationNoteEntity noteEntity = new JobApplicationNoteEntity();
              noteEntity.setMadeByBusiness(businessEntity != null);
              noteEntity.setMadeByFellow(fellowEntity != null);
              noteEntity.setNote(note);
              noteEntity.setApplication(entity);
              noteEntity = jobApplicationNoteRepository.save(noteEntity);
              return noteEntity.getId();
            }).collect(Collectors.toList());
          }).orElseThrow(() -> {
            return new IllegalArgumentException("Fellow is not logged in");
          });
      }).orElseThrow(() -> {
        return new IllegalArgumentException("Job application does not exist: " + jobApplicationId);
      });
  }

  public List<Long> scheduleAppointments(@Argument(name = "jobApplicationId") Long jobApplicationId,
      @Argument(name = "appointments") List<JobInterviewProcessStepAppointment> appointments, DataFetchingEnvironment environment)
      throws Exception {
    Optional<JobApplicationEntity> optionalEntity = jobApplicationRepository.findById(jobApplicationId);
    if (optionalEntity.isEmpty()) {
      throw new IllegalArgumentException("Job application does not exist: " + jobApplicationId);
    }

    JobApplicationEntity jobApplicationEntity = optionalEntity.get();

    return appointments.stream().map(appointment -> {
      InterviewAppointmentEntity entity = new InterviewAppointmentEntity();
      entity.setApplication(jobApplicationEntity);
      entity.setInterviewProcess(
          jobApplicationEntity.getJobListing().getInterviewProcess().stream()
          .filter(foo -> foo.getId() == appointment.getInterviewStepId())
          .findFirst().get()
          );
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
    return authorizationService.getAccount()
        .map(accountEntity -> {
          logger.info("Account: " + accountEntity.getEmail());
          FellowEntity fellowEntity = accountEntity.getFellow();
          Set<JobListingEntity> savedJobs = fellowEntity.getSavedJobs();
          logger.info("Saved jobs: " + savedJobs);

          savedJobs.stream().filter(item -> item.getId() == jobId)
          .findFirst()
          .ifPresentOrElse(jobListingEntity -> {
            // Warning: modifying savedJobs is okay here because filter
            // and findFirst have already completed
            boolean result = savedJobs.removeIf(item -> item.getId() == jobId);
            if (result) {
              fellowEntity.setSavedJobs(savedJobs);
              fellowRepository.save(fellowEntity);
            }
          }, () -> {
            Optional<JobListingEntity> optionalJobListing = jobListingRepository.findById(jobId);
            optionalJobListing.ifPresentOrElse(jobListingEntity -> {
              savedJobs.add(jobListingEntity);
              fellowEntity.setSavedJobs(savedJobs);
              fellowRepository.save(fellowEntity);
            }, () -> {
              throw new IllegalArgumentException("Job does not exist: " + jobId);
            });
          });

          return fellowEntity.getSavedJobs().stream().map(savedJob -> {
            return savedJob.getId();
          }).collect(Collectors.toList());
        }).orElseThrow(() -> {
          return new IllegalArgumentException("Fellow is not logged in");
        });
  }

  public Optional<JobApplicationEntity> getApplication(
    @Argument(name = "id") Long id,
    DataFetchingEnvironment environment) throws Exception {
    logger.info("Enter getApplication");
    return jobApplicationRepository.findById(id);
  }

  public Optional<JobApplicationEntity> rejectApp(
      @Argument(name = "appId") Long appId,
      @Argument(name = "rejectionMessage") String rejectionMessage,
      @Argument(name = "rejectionDetails") String rejectionDetails,
      DataFetchingEnvironment environment) throws Exception {
    logger.info("Enter rejectApp");
    return jobApplicationRepository.findById(appId).map(entity -> {
      entity.setAppIsBeingRejected(true);
      entity.setRejectionMessage(rejectionMessage);
      entity.setRejectionDetails(rejectionDetails);
      entity.setStatus(JobApplicationStatus.REJECTED.getValue());
      return jobApplicationRepository.save(entity);
    });
  }

  public Optional<JobApplicationEntity> highlightApp(
      @Argument(name = "appId") Long appId,
      DataFetchingEnvironment environment) throws Exception {
    logger.info("Enter highlightApp");
    return jobApplicationRepository.findById(appId).map(entity -> {
      entity.setHighlighted(true);
      entity.setStatus(JobApplicationStatus.HIGHLIGHTED.getValue());
      return jobApplicationRepository.save(entity);
    });
  }

  public Optional<JobApplicationEntity> updateStatus(
      @Argument(name = "appId") Long appId,
      @Argument(name = "status") String status,
      DataFetchingEnvironment environment) throws Exception {
    logger.info("Enter updateStatus");
    return jobApplicationRepository.findById(appId).map(entity -> {
      entity.setStatus(status);
      return jobApplicationRepository.save(entity);
    });
  }

  public Optional<JobApplicationEntity> sendJobOffer(
      @Argument(name = "appId") Long appId,
      DataFetchingEnvironment environment) throws Exception {
    logger.info("Enter sendJobOffer");
    return jobApplicationRepository.findById(appId).map(entity -> {
      entity.setJobOfferBeingSent(true);
      entity.setStatus(JobApplicationStatus.OFFERED.getValue());
      return jobApplicationRepository.save(entity);
    });
  }
}
