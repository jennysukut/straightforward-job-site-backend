package com.sfjs.crud.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.conv.AccountConverter;
import com.sfjs.conv.FellowConverter;
import com.sfjs.crud.entity.FellowEntity;
import com.sfjs.crud.repo.BaseRepository;
import com.sfjs.crud.request.FellowRequest;
import com.sfjs.crud.response.FellowResponse;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FellowService extends BaseService<FellowEntity, FellowRequest, FellowResponse> {

  @Autowired
  BaseRepository<FellowEntity> repository;

  public FellowService(AccountConverter accountConverter) {
    super(new FellowConverter(accountConverter));
  }

  @Override
  public BaseRepository<FellowEntity> getBaseRepository() {
    return this.repository;
  }
}
