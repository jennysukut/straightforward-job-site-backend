package com.sfjs.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.conv.AccountConverter;
import com.sfjs.conv.BusinessConverter;
import com.sfjs.dto.Business;
import com.sfjs.entity.BusinessEntity;
import com.sfjs.persist.BasePersist;
import com.sfjs.persist.BusinessPersist;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BusinessService extends BaseService<BusinessEntity, Business> {

  @Autowired
  BusinessPersist repository;

  public BusinessService(AccountConverter accountConverter) {
    super(new BusinessConverter(accountConverter));
  }

  @Override
  public BasePersist<BusinessEntity> getBaseRepository() {
    return this.repository;
  }
}
