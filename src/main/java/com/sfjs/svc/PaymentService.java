package com.sfjs.svc;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

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
import com.sfjs.dto.PaymentResult;
import com.sfjs.dto.PaymentResultInput;
import com.sfjs.dto.request.PaymentRequest;
import com.sfjs.dto.response.PaymentResponse;
import com.sfjs.entity.NumericMetricEntity;
import com.sfjs.entity.PaymentEntity;
import com.sfjs.entity.PaymentStatus;
import com.sfjs.repo.BaseRepository;
import com.sfjs.repo.NumericMetricRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PaymentService extends BaseService<PaymentEntity, PaymentRequest, PaymentResponse> {

  static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

  @Value("${helcim.encrypt.password}")
  private String PASSWORD;

  @Autowired
  BaseRepository<PaymentEntity> repository;

  @Autowired
  private NumericMetricRepository numericMetricRepository;

  public PaymentService(BusinessConverter businessConverter, FellowConverter fellowConverter) {
    super(new PaymentConverter(businessConverter, fellowConverter));
  }

  @Override
  public BaseRepository<PaymentEntity> getBaseRepository() {
    return this.repository;
  }

  public PaymentResult completePayment(PaymentResultInput input) throws JsonProcessingException {
    String cleanedJsonEncodedData = mapper.writeValueAsString(input.getData());
    logger.info("Complete payment: cleaned json data: " + cleanedJsonEncodedData);
    logger.info("Complete payment: hash: " + input.getHash());
    // Need to use the persistence layer to get sensitive data
    // That is not serialized by default
    Optional<PaymentEntity> optional = this.getBaseRepository().findById(input.getPaymentId());
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
      paymentEntity = this.getBaseRepository().save(paymentEntity);
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
