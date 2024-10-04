package com.sfjs.conv;

import org.springframework.stereotype.Service;

import com.sfjs.dto.Business;
import com.sfjs.dto.Fellow;
import com.sfjs.dto.Payment;
import com.sfjs.entity.BusinessEntity;
import com.sfjs.entity.FellowEntity;
import com.sfjs.entity.PaymentEntity;
import com.sfjs.entity.PaymentStatus;

@Service
public class PaymentConverter extends BaseConverter<PaymentEntity, Payment> {

  BusinessConverter businessConverter;
  FellowConverter fellowConverter;

  public PaymentConverter(BusinessConverter businessConverter, FellowConverter fellowConverter) {
    super(PaymentEntity.class, Payment.class);
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
  public PaymentEntity convertToEntity(Payment payment) {
    // Default conversion
    PaymentEntity entity = super.convertToEntity(payment);

    // Fields that are not directly mapped by default conversion
    entity.setSALT(payment.getSALT());
    entity.setSecretToken(payment.getSecretToken());
    entity.setStatus(stringToEnum(PaymentStatus.class, payment.getStatus()));

    if (payment.getBusinessName() != null) {
      // Create a business entity with business name and email
      // This is all the information we have about the business and account
      Business business = new Business();
      business.setBusiness(payment.getBusinessName());
      business.setEmail(payment.getEmail());
      BusinessEntity businessEntity = businessConverter.convertToEntity(business);
      entity.setBusiness(businessEntity);
    } else if (payment.getFellowName() != null) {
      // Create a fellow entity with name and email
      // This is all the information we have about the fellow and account
      Fellow fellow = new Fellow();
      fellow.setName(payment.getFellowName());
      fellow.setEmail(payment.getEmail());
      FellowEntity fellowEntity = fellowConverter.convertToEntity(fellow);
      entity.setFellow(fellowEntity);
    }

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
    if (entity.getBusiness() != null) {
      payment.setBusinessName(entity.getBusiness().getName());
      payment.setEmail(entity.getBusiness().getAccount().getEmail());
    } else if (entity.getFellow() != null) {
      payment.setFellowName(entity.getFellow().getName());
      payment.setEmail(entity.getFellow().getAccount().getEmail());
    }
    payment.setStatus(entity.getStatus() != null ? entity.getStatus().name() : PaymentStatus.PENDING.name());
    return payment;
  }
}
