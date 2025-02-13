package com.sfjs.conv;

import org.springframework.stereotype.Service;

import com.sfjs.data.BusinessData;
import com.sfjs.jpa.entity.BusinessEntity;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BusinessConverter extends BaseConverter<BusinessEntity, BusinessData> {

  AccountConverter accountConverter;

  public BusinessConverter(AccountConverter accountConverter) {
    super(BusinessData.class);
    this.accountConverter = accountConverter;
  }
}
