package com.sfjs.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.conv.AccountConverter;
import com.sfjs.conv.FellowConverter;
import com.sfjs.dto.Fellow;
import com.sfjs.dto.response.FellowResponse;
import com.sfjs.entity.FellowEntity;
import com.sfjs.repo.BaseRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FellowService extends BaseService<FellowEntity, Fellow, FellowResponse> {

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
