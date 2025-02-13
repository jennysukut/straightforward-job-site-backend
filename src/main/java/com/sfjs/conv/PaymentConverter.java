package com.sfjs.conv;

import org.springframework.stereotype.Service;

import com.sfjs.data.PaymentData;
import com.sfjs.jpa.entity.PaymentEntity;
import com.sfjs.jpa.entity.PaymentStatus;

@Service
public class PaymentConverter extends BaseConverter<PaymentEntity, PaymentData> {

  BusinessConverter businessConverter;
  FellowConverter fellowConverter;

  public PaymentConverter(BusinessConverter businessConverter, FellowConverter fellowConverter) {
    super(PaymentData.class);
    this.businessConverter = businessConverter;
    this.fellowConverter = fellowConverter;
  }

  /**
   * This maps a PaymentEntity object to a PaymentResponse dto
   *
   * This function is necessary because the status can be null
   *
   * @param entity - PaymentEntity object
   * @return Payment - data transfer object
   */
  @Override
  public PaymentData convertToBody(PaymentEntity entity) {
    // Default conversion
    PaymentData payment = super.convertToBody(entity);
    payment.setStatus(entity.getStatus() != null ? entity.getStatus() : PaymentStatus.PENDING);
    return payment;
  }
}
