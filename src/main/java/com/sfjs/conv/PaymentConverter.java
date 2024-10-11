package com.sfjs.conv;

import org.springframework.stereotype.Service;

import com.sfjs.dto.request.BusinessRequest;
import com.sfjs.dto.request.FellowRequest;
import com.sfjs.dto.request.PaymentRequest;
import com.sfjs.dto.response.PaymentResponse;
import com.sfjs.entity.BusinessEntity;
import com.sfjs.entity.FellowEntity;
import com.sfjs.entity.PaymentEntity;
import com.sfjs.entity.PaymentStatus;

@Service
public class PaymentConverter extends BaseConverter<PaymentEntity, PaymentRequest, PaymentResponse> {

  BusinessConverter businessConverter;
  FellowConverter fellowConverter;

  public PaymentConverter(BusinessConverter businessConverter, FellowConverter fellowConverter) {
    super(PaymentEntity.class, PaymentResponse.class);
    this.businessConverter = businessConverter;
    this.fellowConverter = fellowConverter;
  }

  /**
   * This maps a Payment dto to PaymentEntity
   *
   * This function is necessary because the dto object is flattened; whereas, the
   * entity is not
   *
   * @param payment - Payment data transfer object
   * @return PaymentEntity
   */
  @Override
  public PaymentEntity convertToEntity(PaymentRequest payment) {
    // Default conversion
    PaymentEntity entity = super.convertToEntity(payment);

    // Fields that are not directly mapped by default conversion
    entity.setSALT(payment.getSALT());
    entity.setSecretToken(payment.getSecretToken());
    entity.setStatus(stringToEnum(PaymentStatus.class, payment.getStatus()));

    if (payment.getBusinessName() != null) {
      // Create a business entity with business name and email
      // This is all the information we have about the business and account
      BusinessRequest business = new BusinessRequest();
      business.setBusiness(payment.getBusinessName());
      business.setEmail(payment.getEmail());
      BusinessEntity businessEntity = businessConverter.convertToEntity(business);
      entity.setBusiness(businessEntity);
    } else if (payment.getFellowName() != null) {
      // Create a fellow entity with name and email
      // This is all the information we have about the fellow and account
      FellowRequest fellow = new FellowRequest();
      fellow.setName(payment.getFellowName());
      fellow.setEmail(payment.getEmail());
      FellowEntity fellowEntity = fellowConverter.convertToEntity(fellow);
      entity.setFellow(fellowEntity);
    }

    return entity;
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
