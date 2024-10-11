package com.sfjs.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.conv.AccountConverter;
import com.sfjs.conv.BusinessConverter;
import com.sfjs.dto.Business;
import com.sfjs.dto.response.BusinessResponse;
import com.sfjs.entity.BusinessEntity;
import com.sfjs.repo.BaseRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BusinessService extends BaseService<BusinessEntity, Business, BusinessResponse> {

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
