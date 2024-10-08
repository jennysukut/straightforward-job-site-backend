package com.sfjs.svc;

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
import com.sfjs.entity.FellowEntity;
import com.sfjs.entity.PaymentEntity;
import com.sfjs.entity.PaymentStatus;
import com.sfjs.repo.FellowRepository;
import com.sfjs.repo.PaymentRepository;

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
  PaymentService paymentService;

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

  Logger logger = Logger.getLogger(getClass().getName());

  public Mono<Payment> acceptBusinessDonation(BusinessDonation donation) {

    logger.info("Starting initialize");
    Payment payment = new Payment();
    payment.setAmount(donation.getAmount());
    payment.setCurrency("USD");
    payment.setEmail(donation.getEmail());
    payment.setPaymentType("purchase");
    payment.setBusinessName(donation.getBusinessName());
    payment.setStatus(PaymentStatus.PENDING.name());

    return helcimService.initializeCheckout(payment).flatMap(response -> {
      logger.info("Response: " + response);
      // Save a field to the database
      return Mono.fromCallable(() -> {
        logger.info("fromCallable");
        Business business = new Business();
        business.setBusiness(donation.getBusinessName());
        business.setEmail(donation.getEmail());
        business.setContactName(donation.getContactName());
        business.setReferral(donation.getReferral());
        businessService.customSave(business);

        // update this one field from response from helcim service
        String rawToken = response.getSecretToken();
        String SALT = KeyGenerators.string().generateKey();
        payment.setSALT(SALT);
        TextEncryptor encryptor = Encryptors.text(PASSWORD, SALT);
        String encryptedToken = encryptor.encrypt(rawToken);
        payment.setSecretToken(encryptedToken);
        // save the entity and return it
        logger.info("Save payment: " + payment);
        return paymentService.customSave(payment);
      }).map(anotherPayment -> {
        anotherPayment.setCheckoutToken(response.getCheckoutToken());
        return anotherPayment;
      });
    });
  }

  public Mono<Payment> acceptFellowDonation(FellowDonation donation) {

    logger.info("Implicit fellow signup");
    Fellow fellow = new Fellow();
    fellow.setName(donation.getName());
    fellow.setEmail(donation.getEmail());
    fellow = signupService.signupFellow(fellow);

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
