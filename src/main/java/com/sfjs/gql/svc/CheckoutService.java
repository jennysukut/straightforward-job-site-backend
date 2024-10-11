package com.sfjs.gql.svc;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import com.sfjs.conv.PaymentConverter;
import com.sfjs.dto.Business;
import com.sfjs.dto.BusinessDonation;
import com.sfjs.dto.Fellow;
import com.sfjs.dto.FellowDonation;
import com.sfjs.dto.Payment;
import com.sfjs.dto.response.BusinessResponse;
import com.sfjs.dto.response.FellowResponse;
import com.sfjs.dto.response.PaymentResponse;
import com.sfjs.entity.BusinessEntity;
import com.sfjs.entity.FellowEntity;
import com.sfjs.entity.PaymentEntity;
import com.sfjs.repo.BusinessRepository;
import com.sfjs.repo.FellowRepository;
import com.sfjs.repo.PaymentRepository;
import com.sfjs.svc.BusinessService;
import com.sfjs.svc.FellowService;

import jakarta.transaction.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class CheckoutService {

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
  PaymentConverter paymentConverter;

  @Autowired
  FellowRepository fellowRepository;

  @Autowired
  PaymentRepository paymentRepository;

  @Autowired
  BusinessRepository businessRepository;

  Logger logger = Logger.getLogger(getClass().getName());

  public Mono<PaymentResponse> acceptBusinessDonation(BusinessDonation donation) {

    logger.info("Implicit business signup");
    Business business = new Business();
    business.setBusiness(donation.getBusinessName());
    business.setEmail(donation.getEmail());
    business.setContactName(donation.getContactName());
    business.setReferral(donation.getReferral());
    BusinessResponse businessResponse = signupService.signupBusiness(business);
    business.setId(businessResponse.getId());

    Payment payment = new Payment();
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
        PaymentEntity paymentEntity = paymentConverter.convertToEntity(payment);
        Optional<BusinessEntity> businessEntity = businessRepository.findById(payment.getBusiness().getId());
        paymentEntity.setBusiness(businessEntity.get());
        PaymentEntity savedPaymentEntity = paymentRepository.save(paymentEntity);
        return paymentConverter.convertToBody(savedPaymentEntity);
      }).map(anotherPayment -> {
        anotherPayment.setCheckoutToken(response.getCheckoutToken());
        return anotherPayment;
      });
    });
  }

  public Mono<PaymentResponse> acceptFellowDonation(FellowDonation donation) {

    logger.info("Implicit fellow signup");
    Fellow fellow = new Fellow();
    fellow.setName(donation.getName());
    fellow.setEmail(donation.getEmail());
    FellowResponse fellowResponse = signupService.signupFellow(fellow);
    fellow.setId(fellowResponse.getId());

    Payment payment = new Payment();
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
        PaymentEntity paymentEntity = paymentConverter.convertToEntity(payment);
        Optional<FellowEntity> fellowEntity = fellowRepository.findById(payment.getFellow().getId());
        paymentEntity.setFellow(fellowEntity.get());
        PaymentEntity savedPaymentEntity = paymentRepository.save(paymentEntity);
        return paymentConverter.convertToBody(savedPaymentEntity);
      }).map(anotherPayment -> {
        anotherPayment.setCheckoutToken(response.getCheckoutToken());
        return anotherPayment;
      });
    });
  }
}
