package com.sfjs.conv;

import org.springframework.stereotype.Service;

import com.sfjs.crud.entity.FellowEntity;
import com.sfjs.crud.response.FellowResponse;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FellowConverter extends BaseConverter<FellowEntity, FellowResponse> {

  AccountConverter accountConverter;

  public FellowConverter(AccountConverter accountConverter) {
    super(FellowResponse.class);
    this.accountConverter = accountConverter;
  }
}
