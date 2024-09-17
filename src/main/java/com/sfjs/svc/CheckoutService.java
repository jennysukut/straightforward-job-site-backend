package com.sfjs.svc;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import com.sfjs.dto.Payment;

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

  Logger logger = Logger.getLogger(getClass().getName());

  public Mono<Payment> initializePayment(Payment payment) {

    logger.info("Starting initialize");

    return helcimService.initializeCheckout(payment).flatMap(response -> {
      // Save a field to the database
      return Mono.fromCallable(() -> {
        // update this one field from response from helcim service
        String rawToken = response.getSecretToken();
        String SALT = KeyGenerators.string().generateKey();
        payment.setSALT(SALT);
        TextEncryptor encryptor = Encryptors.text(PASSWORD, SALT);
        String encryptedToken = encryptor.encrypt(rawToken);
        payment.setSecretToken(encryptedToken);
        // save the entity and return it
        return paymentService.customSave(payment);
      }).map(anotherPayment -> {
        anotherPayment.setCheckoutToken(response.getCheckoutToken());
        return anotherPayment;
      });
    });
  }

}
