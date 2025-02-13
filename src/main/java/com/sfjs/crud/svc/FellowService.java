package com.sfjs.crud.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.conv.AccountConverter;
import com.sfjs.conv.FellowConverter;
import com.sfjs.data.api.FellowData;
import com.sfjs.data.entity.FellowEntity;
import com.sfjs.jpa.repo.BaseRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FellowService extends BaseService<FellowEntity, FellowData> {

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
