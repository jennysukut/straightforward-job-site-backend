package com.sfjs.crud.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.conv.AccountConverter;
import com.sfjs.conv.BusinessConverter;
import com.sfjs.data.api.BusinessData;
import com.sfjs.data.entity.BusinessEntity;
import com.sfjs.jpa.repo.BaseRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BusinessService extends BaseService<BusinessEntity, BusinessData> {

  @Autowired
  BaseRepository<BusinessEntity> repository;

  public BusinessService(AccountConverter accountConverter) {
    super(new BusinessConverter(accountConverter));
  }

  @Override
  public BaseRepository<BusinessEntity> getBaseRepository() {
    return this.repository;
  }
}
