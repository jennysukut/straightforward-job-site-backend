package com.sfjs.crud.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.conv.BusinessConverter;
import com.sfjs.conv.FellowConverter;
import com.sfjs.conv.PaymentConverter;
import com.sfjs.crud.entity.PaymentEntity;
import com.sfjs.crud.repo.BaseRepository;
import com.sfjs.crud.response.PaymentResponse;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PaymentService extends BaseService<PaymentEntity, PaymentResponse> {

  @Autowired
  BaseRepository<PaymentEntity> repository;

  public PaymentService(BusinessConverter businessConverter, FellowConverter fellowConverter) {
    super(new PaymentConverter(businessConverter, fellowConverter));
  }

  @Override
  public BaseRepository<PaymentEntity> getBaseRepository() {
    return this.repository;
  }
}
