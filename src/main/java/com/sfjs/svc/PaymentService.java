package com.sfjs.svc;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sfjs.conv.BusinessConverter;
import com.sfjs.conv.FellowConverter;
import com.sfjs.conv.PaymentConverter;
import com.sfjs.dto.Payment;
import com.sfjs.dto.PaymentResult;
import com.sfjs.dto.PaymentResultInput;
import com.sfjs.entity.PaymentEntity;
import com.sfjs.entity.PaymentStatus;
import com.sfjs.persist.BasePersist;
import com.sfjs.persist.PaymentPersist;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PaymentService extends BaseService<PaymentEntity, Payment> {

  static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

  @Value("${helcim.encrypt.password}")
  private String PASSWORD;

  @Autowired
  PaymentPersist repository;

  public PaymentService(BusinessConverter businessConverter,
      FellowConverter fellowConverter) {
    super(new PaymentConverter(businessConverter, fellowConverter));
  }

  @Override
  public BasePersist<PaymentEntity> getBaseRepository() {
    return this.repository;
  }

  public PaymentResult completePayment(PaymentResultInput input) throws JsonProcessingException {
    String cleanedJsonEncodedData = mapper.writeValueAsString(input.getData());
    logger.info("Complete payment: cleaned json data: " + cleanedJsonEncodedData);
    logger.info("Complete payment: hash: " + input.getHash());
    // Need to use the persistence layer to get sensitive data
    // That is not serialized by default
    PaymentEntity paymentEntity = this.getBaseRepository().getById(input.getPaymentId());
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
      paymentEntity = this.getBaseRepository().customSave(paymentEntity);
    } else {
      result.setSuccess(false);
    }
    result.setPayment(createBody(paymentEntity));
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
