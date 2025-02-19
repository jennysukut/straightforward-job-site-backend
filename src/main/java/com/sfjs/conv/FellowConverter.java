package com.sfjs.conv;

import org.springframework.stereotype.Service;

import com.sfjs.data.api.FellowData;
import com.sfjs.data.entity.FellowEntity;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FellowConverter extends BaseConverter<FellowEntity, FellowData> {

  AccountConverter accountConverter;

  public FellowConverter(AccountConverter accountConverter) {
    super(FellowData.class);
    this.accountConverter = accountConverter;
  }
}
