package com.sfjs.gql.svc;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sfjs.crud.entity.BusinessEntity;
import com.sfjs.crud.entity.FellowEntity;
import com.sfjs.crud.entity.NumericMetricEntity;
import com.sfjs.crud.entity.PaymentEntity;
import com.sfjs.crud.entity.PaymentStatus;
import com.sfjs.crud.repo.BusinessRepository;
import com.sfjs.crud.repo.FellowRepository;
import com.sfjs.crud.repo.NumericMetricRepository;
import com.sfjs.crud.repo.PaymentRepository;
import com.sfjs.crud.response.PaymentResponse;
import com.sfjs.crud.svc.BusinessService;
import com.sfjs.crud.svc.FellowService;
import com.sfjs.gql.schema.BusinessDonation;
import com.sfjs.gql.schema.BusinessInput;
import com.sfjs.gql.schema.FellowDonation;
import com.sfjs.gql.schema.FellowInput;
import com.sfjs.gql.schema.PaymentInput;
import com.sfjs.gql.schema.PaymentResult;
import com.sfjs.gql.schema.PaymentResultInput;

import jakarta.transaction.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class CheckoutService {

  static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

  @Value("${helcim.encrypt.password}")
  private String PASSWORD;

  @Autowired
  HelcimService helcimService;

  @Autowired
  BusinessService businessService;

  @Autowired
  FellowService fellowService;

  @Autowired
  SignupService signupService;

  @Autowired
  FellowRepository fellowRepository;

  @Autowired
  PaymentRepository paymentRepository;

  @Autowired
  BusinessRepository businessRepository;

  @Autowired
  NumericMetricRepository numericMetricRepository;

  Logger logger = Logger.getLogger(getClass().getName());

  public Mono<PaymentResponse> acceptBusinessDonation(BusinessDonation donation) {

    logger.info("Implicit business signup");
    BusinessInput business = new BusinessInput();
    business.setBusiness(donation.getBusinessName());
    business.setEmail(donation.getEmail());
    business.setContactName(donation.getContactName());
    business.setReferral(donation.getReferral());
    Long businessId = signupService.signupBusiness(business);
    business.setId(businessId);

    PaymentInput payment = new PaymentInput();
    payment.setBusiness(business);
    payment.setAmount(donation.getAmount());
    payment.setCurrency("USD");
    payment.setEmail(donation.getEmail());
    payment.setPaymentType("purchase");
    payment.setBusinessName(donation.getBusinessName());

    return helcimService.initializeCheckout(payment).flatMap(response -> {
      logger.info("Response: " + response);
      // Save a field to the database
      return Mono.fromCallable(() -> {
        logger.info("fromCallable");

        // update this one field from response from helcim service
        String rawToken = response.getSecretToken();
        logger.info("Raw token: " + rawToken);
        String SALT = KeyGenerators.string().generateKey();
        logger.info("Encryption SALT: " + SALT);
        payment.setSALT(SALT);
        logger.info("Encryption password: " + PASSWORD);
        TextEncryptor encryptor = Encryptors.text(PASSWORD, SALT);
        String encryptedToken = encryptor.encrypt(rawToken);
        payment.setSecretToken(encryptedToken);
        // save the entity and return it
        logger.info("Save payment: " + payment);
        // TODO this is where we need to create/convert to entity
        PaymentEntity paymentEntity = new PaymentEntity(); // paymentConverter.convertToEntity(payment);

        paymentEntity.setAmount(payment.getAmount());
        paymentEntity.setCurrency(payment.getCurrency());
        paymentEntity.setPaymentType(payment.getPaymentType());

        paymentEntity.setSALT(payment.getSALT());
        paymentEntity.setSecretToken(payment.getSecretToken());
        paymentEntity.setStatus(PaymentStatus.PENDING);

        Optional<BusinessEntity> businessEntity = businessRepository.findById(businessId);
        paymentEntity.setBusiness(businessEntity.get());
        PaymentEntity savedPaymentEntity = paymentRepository.save(paymentEntity);
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setId(savedPaymentEntity.getId());
        paymentResponse.setStatus(savedPaymentEntity.getStatus());
        return paymentResponse;
      }).map(anotherPayment -> {
        anotherPayment.setCheckoutToken(response.getCheckoutToken());
        return anotherPayment;
      });
    });
  }

  public Mono<PaymentResponse> acceptFellowDonation(FellowDonation donation) {

    logger.info("Implicit fellow signup");
    FellowInput fellow = new FellowInput();
    fellow.setName(donation.getName());
    fellow.setEmail(donation.getEmail());
    Long fellowId = signupService.signupFellow(fellow);
    fellow.setId(fellowId);

    PaymentInput payment = new PaymentInput();
    payment.setFellow(fellow);
    payment.setAmount(donation.getAmount());
    payment.setCurrency("USD");
    payment.setEmail(donation.getEmail());
    payment.setPaymentType("purchase");
    payment.setFellowName(donation.getName());

    return helcimService.initializeCheckout(payment).flatMap(response -> {
      logger.info("Response: " + response);
      // Save a field to the database
      return Mono.fromCallable(() -> {
        logger.info("fromCallable");

        // update this one field from response from helcim service
        String rawToken = response.getSecretToken();
        logger.info("Raw token: " + rawToken);
        String SALT = KeyGenerators.string().generateKey();
        logger.info("Encryption SALT: " + SALT);
        payment.setSALT(SALT);
        logger.info("Encryption password: " + PASSWORD);
        TextEncryptor encryptor = Encryptors.text(PASSWORD, SALT);
        String encryptedToken = encryptor.encrypt(rawToken);
        payment.setSecretToken(encryptedToken);
        // save the entity and return it
        logger.info("Save payment: " + payment);
        // TODO this is where we need to create/convert to entity
        PaymentEntity paymentEntity = new PaymentEntity(); // paymentConverter.convertToEntity(payment);

        paymentEntity.setAmount(payment.getAmount());
        paymentEntity.setCurrency(payment.getCurrency());
        paymentEntity.setPaymentType(payment.getPaymentType());

        paymentEntity.setSALT(payment.getSALT());
        paymentEntity.setSecretToken(payment.getSecretToken());
        paymentEntity.setStatus(PaymentStatus.PENDING);

        Optional<FellowEntity> fellowEntity = fellowRepository.findById(fellowId);
        paymentEntity.setFellow(fellowEntity.get());
        PaymentEntity savedPaymentEntity = paymentRepository.save(paymentEntity);
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setId(savedPaymentEntity.getId());
        paymentResponse.setStatus(savedPaymentEntity.getStatus());
        return paymentResponse;
      }).map(anotherPayment -> {
        anotherPayment.setCheckoutToken(response.getCheckoutToken());
        return anotherPayment;
      });
    });
  }

  public PaymentResult completePayment(PaymentResultInput input) throws JsonProcessingException {
    String cleanedJsonEncodedData = mapper.writeValueAsString(input.getData());
    logger.info("Complete payment: cleaned json data: " + cleanedJsonEncodedData);
    logger.info("Complete payment: hash: " + input.getHash());
    // Need to use the persistence layer to get sensitive data
    // That is not serialized by default
    Optional<PaymentEntity> optional = paymentRepository.findById(input.getPaymentId());
    if (optional.isEmpty()) {
      PaymentResult result = new PaymentResult();
      result.setSuccess(false);
      result.setMessage("Payment not found by id");
      return result;
    }
    PaymentEntity paymentEntity = optional.get();
    logger.info("Complete payment: Payment entity: " + paymentEntity);
    TextEncryptor encryptor = Encryptors.text(PASSWORD, paymentEntity.getSALT());
    String secretToken = encryptor.decrypt(paymentEntity.getSecretToken());
    logger.info("Complete payment: secret token: " + secretToken);
    String expectedHash = sha256(cleanedJsonEncodedData + secretToken);
    logger.info("Complete payment: Expected hash: " + expectedHash);
    PaymentResult result = new PaymentResult();
    if (input.getHash().contentEquals(expectedHash)) {
      result.setSuccess(true);
      paymentEntity.setStatus(PaymentStatus.APPROVED);
      paymentEntity = paymentRepository.save(paymentEntity);
    } else {
      result.setSuccess(false);
    }
    if (paymentEntity.getFellow() != null) {
      NumericMetricEntity metric = numericMetricRepository.findByName("CURRENT_FELLOW_DONATION");
      metric.setMetric(metric.getMetric().add(new BigDecimal(paymentEntity.getAmount())));
      numericMetricRepository.save(metric);
    } else if (paymentEntity.getBusiness() != null) {
      NumericMetricEntity metric = numericMetricRepository.findByName("CURRENT_BUSINESS_DONATION");
      metric.setMetric(metric.getMetric().add(new BigDecimal(paymentEntity.getAmount())));
      numericMetricRepository.save(metric);
    }
    return result;
  }

  private static String sha256(String input) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(input.getBytes());
      StringBuilder hexString = new StringBuilder();
      for (byte b : hash) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1)
          hexString.append('0');
        hexString.append(hex);
      }
      return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }
}
