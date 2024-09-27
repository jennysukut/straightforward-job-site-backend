package com.sfjs.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.conv.BusinessConverter;
import com.sfjs.conv.FellowConverter;
import com.sfjs.conv.PaymentConverter;
import com.sfjs.dto.Payment;
import com.sfjs.entity.PaymentEntity;
import com.sfjs.persist.BasePersist;
import com.sfjs.persist.PaymentPersist;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PaymentService extends BaseService<PaymentEntity, Payment> {

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
}
