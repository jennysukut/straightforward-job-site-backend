package com.sfjs.crud.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.conv.AccountConverter;
import com.sfjs.conv.BusinessConverter;
import com.sfjs.crud.entity.BusinessEntity;
import com.sfjs.crud.repo.BaseRepository;
import com.sfjs.crud.request.BusinessRequest;
import com.sfjs.crud.response.BusinessResponse;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BusinessService extends BaseService<BusinessEntity, BusinessRequest, BusinessResponse> {

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
