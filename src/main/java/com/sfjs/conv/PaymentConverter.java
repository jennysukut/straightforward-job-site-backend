package com.sfjs.conv;

import org.springframework.stereotype.Service;

import com.sfjs.crud.entity.PaymentEntity;
import com.sfjs.crud.entity.PaymentStatus;
import com.sfjs.crud.response.PaymentResponse;

@Service
public class PaymentConverter extends BaseConverter<PaymentEntity, PaymentResponse> {

  BusinessConverter businessConverter;
  FellowConverter fellowConverter;

  public PaymentConverter(BusinessConverter businessConverter, FellowConverter fellowConverter) {
    super(PaymentResponse.class);
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
  public PaymentResponse convertToBody(PaymentEntity entity) {
    // Default conversion
    PaymentResponse payment = super.convertToBody(entity);
    payment.setStatus(entity.getStatus() != null ? entity.getStatus() : PaymentStatus.PENDING);
    return payment;
  }
}
