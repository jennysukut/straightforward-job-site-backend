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
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sfjs.data.core.ClientCheckoutData;
import com.sfjs.data.core.PaymentStatus;
import com.sfjs.data.entity.BusinessEntity;
import com.sfjs.data.entity.FellowEntity;
import com.sfjs.data.entity.NumericMetricEntity;
import com.sfjs.data.entity.PaymentEntity;
import com.sfjs.jpa.repo.BusinessRepository;
import com.sfjs.jpa.repo.FellowRepository;
import com.sfjs.jpa.repo.NumericMetricRepository;
import com.sfjs.jpa.repo.PaymentRepository;

import reactor.core.publisher.Mono;

@Service
@Transactional
public class CheckoutService {

  static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

  @Value("${helcim.encrypt.password}")
  private String PASSWORD;

  @Autowired
  HelcimService helcimService;

//  @Autowired
//  BusinessService businessService;

//  @Autowired
//  FellowService fellowService;

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

  public Mono<ClientCheckoutData> acceptBusinessDonation(
      String email, String password, String name,
      Boolean isBetaTester, String contactName, Boolean isEarlySignup, String referral,
      String amount, String currency, String paymentType) {

    logger.info("Implicit business signup");
//    BusinessInput business = new BusinessInput();
//    business.setBusinessName(donation.getBusinessName());
//    business.setEmail(donation.getEmail());
//    business.setContactName(donation.getContactName());
//    business.setReferral(donation.getReferral());
    Long businessId = signupService.signupBusiness(email, password, name, isBetaTester, contactName, isEarlySignup, referral);
//    business.setId(businessId);

//    PaymentInput payment = new PaymentInput();
//    payment.setBusiness(business);
//    payment.setAmount(donation.getAmount());
//    payment.setCurrency("USD");
//    payment.setEmail(donation.getEmail());
//    payment.setPaymentType("purchase");
//    payment.setBusinessName(donation.getBusinessName());

    return helcimService.initializeCheckout(amount, currency, paymentType).flatMap(helcimResponse -> {
      logger.info("Helcim response: " + helcimResponse);
      // Save a field to the database
      return Mono.fromCallable(() -> {
        PaymentEntity paymentEntity = createPaymentEntity(amount, currency, paymentType, helcimResponse.getCheckoutToken());
        Optional<BusinessEntity> businessEntity = businessRepository.findById(businessId);
        paymentEntity.setBusiness(businessEntity.get());
        PaymentEntity savedPaymentEntity = paymentRepository.save(paymentEntity);
        ClientCheckoutData clientCheckoutData = new ClientCheckoutData();
        clientCheckoutData.setId(savedPaymentEntity.getId());
        clientCheckoutData.setStatus(savedPaymentEntity.getStatus());
        clientCheckoutData.setCheckoutToken(helcimResponse.getCheckoutToken());
        return clientCheckoutData;
      });
    });
  }

  private PaymentEntity createPaymentEntity(String amount, String currency, String paymentType, String secretToken) {
    logger.info("fromCallable");

    // update this one field from response from helcim service
//    String secretToken = response.getSecretToken();
    logger.info("Raw token: " + secretToken);
    String SALT = KeyGenerators.string().generateKey();
    logger.info("Encryption SALT: " + SALT);
    // TODO examine this
//        payment.setSALT(SALT);
    logger.info("Encryption password: " + PASSWORD);
    TextEncryptor encryptor = Encryptors.text(PASSWORD, SALT);
    String encryptedSecretToken = encryptor.encrypt(secretToken);
    // TODO examine this
//        payment.setSecretToken(encryptedToken);
    // save the entity and return it
//        logger.info("Save payment: " + payment);
    // TODO this is where we need to create/convert to entity
    PaymentEntity paymentEntity = new PaymentEntity(); // paymentConverter.convertToEntity(payment);

    paymentEntity.setAmount(amount);
    paymentEntity.setCurrency(currency);
    paymentEntity.setPaymentType(paymentType);

    paymentEntity.setSALT(SALT);
    paymentEntity.setSecretToken(encryptedSecretToken);
    paymentEntity.setStatus(PaymentStatus.PENDING);
    return paymentEntity;
  }

  public Mono<ClientCheckoutData> acceptFellowDonation(
      String email, String password, String name,
      Boolean isBetaTester, boolean isCollaborator, String message, String referralCode, boolean isReferralPartner,
      String amount, String currency, String paymentType) {

    logger.info("Implicit fellow signup");
//    FellowInput fellow = new FellowInput();
//    fellow.setName(donation.getName());
//    fellow.setEmail(donation.getEmail());
    Long fellowId = signupService.signupFellow(email, password, name, isBetaTester, isCollaborator, message, referralCode, isReferralPartner);
//    fellow.setId(fellowId);

//    PaymentInput payment = new PaymentInput();
//    payment.setFellow(fellow);
//    payment.setAmount(donation.getAmount());
//    payment.setCurrency("USD");
//    payment.setEmail(donation.getEmail());
//    payment.setPaymentType("purchase");
//    payment.setFellowName(donation.getName());

    return helcimService.initializeCheckout(amount, currency, paymentType).flatMap(response -> {
      logger.info("Response: " + response);
      // Save a field to the database
      return Mono.fromCallable(() -> {
        PaymentEntity paymentEntity = createPaymentEntity(amount, currency, paymentType, response.getCheckoutToken());
        Optional<FellowEntity> fellowEntity = fellowRepository.findById(fellowId);
        paymentEntity.setFellow(fellowEntity.get());
        PaymentEntity savedPaymentEntity = paymentRepository.save(paymentEntity);
        ClientCheckoutData paymentResponse = new ClientCheckoutData();
        paymentResponse.setId(savedPaymentEntity.getId());
        paymentResponse.setStatus(savedPaymentEntity.getStatus());
        return paymentResponse;
      }).map(anotherPayment -> {
        anotherPayment.setCheckoutToken(response.getCheckoutToken());
        return anotherPayment;
      });
    });
  }

  public PaymentStatus completePayment(String cleanedJsonEncodedData, String hash, Long paymentId) throws JsonProcessingException {
//    String cleanedJsonEncodedData = mapper.writeValueAsString(input.getData());
    logger.info("Complete payment: cleaned json data: " + cleanedJsonEncodedData);
    logger.info("Complete payment: hash: " + hash);
    // Need to use the persistence layer to get sensitive data
    // That is not serialized by default
    Optional<PaymentEntity> optional = paymentRepository.findById(paymentId);
    if (optional.isEmpty()) {
      PaymentStatus result = PaymentStatus.PENDING;
      return result;
    }
    PaymentEntity paymentEntity = optional.get();
    logger.info("Complete payment: Payment entity: " + paymentEntity);
    TextEncryptor encryptor = Encryptors.text(PASSWORD, paymentEntity.getSALT());
    String secretToken = encryptor.decrypt(paymentEntity.getSecretToken());
    logger.info("Complete payment: secret token: " + secretToken);
    String expectedHash = sha256(cleanedJsonEncodedData + secretToken);
    logger.info("Complete payment: Expected hash: " + expectedHash);
    PaymentStatus result;
    if (hash.contentEquals(expectedHash)) {
      result = PaymentStatus.APPROVED;
      paymentEntity.setStatus(result);
      paymentEntity = paymentRepository.save(paymentEntity);
    } else {
      result = PaymentStatus.PENDING;
//      result.setSuccess(false);
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
