package com.sfjs.conv;

import org.springframework.stereotype.Service;

import com.sfjs.crud.entity.BusinessEntity;
import com.sfjs.crud.response.BusinessResponse;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BusinessConverter extends BaseConverter<BusinessEntity, BusinessResponse> {

  AccountConverter accountConverter;

  public BusinessConverter(AccountConverter accountConverter) {
    super(BusinessResponse.class);
    this.accountConverter = accountConverter;
  }
}
