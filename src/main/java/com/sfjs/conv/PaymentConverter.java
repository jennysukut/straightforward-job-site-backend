package com.sfjs.conv;

import org.springframework.stereotype.Service;

import com.sfjs.dto.Business;
import com.sfjs.dto.Payment;
import com.sfjs.entity.BusinessEntity;
import com.sfjs.entity.PaymentEntity;

@Service
public class PaymentConverter extends BaseConverter<PaymentEntity, Payment> {

  BusinessConverter businessConverter;

  public PaymentConverter(BusinessConverter businessConverter) {
    super(PaymentEntity.class, Payment.class);
    this.businessConverter = businessConverter;
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
  public PaymentEntity convertToEntity(Payment payment) {
    // Create a business entity with business name and email
    // This is all the information we have about the business and account
    Business business = new Business();
    business.setBusiness(payment.getBusinessName());
    business.setEmail(payment.getEmail());
    BusinessEntity businessEntity = businessConverter.convertToEntity(business);

    // Default conversion
    PaymentEntity entity = super.convertToEntity(payment);

    // Fields that are not directly mapped by default conversion
    entity.setBusiness(businessEntity);
    entity.setSALT(payment.getSALT());
    entity.setSecretToken(payment.getSecretToken());

    return entity;
  }

  /**
   * This maps a PaymentEntity object to a Payment dto
   *
   * This function is necessary because the dto is flattened; whereas, the entity
   * is not
   *
   * @param entity - PaymentEntity object
   * @return Payment - data transfer object
   */
  @Override
  public Payment convertToBody(PaymentEntity entity) {
    // Default conversion
    Payment payment = super.convertToBody(entity);
    // Fields that are not directly mapped by default conversion
    payment.setBusinessName(entity.getBusiness().getName());
    payment.setEmail(entity.getBusiness().getAccount().getEmail());
    return payment;
  }

}
