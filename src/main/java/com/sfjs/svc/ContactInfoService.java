package com.sfjs.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.entity.ContactInfoEntity;
import com.sfjs.repo.BaseRepository;
import com.sfjs.repo.ContactInfoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ContactInfoService extends BaseService<ContactInfoEntity> {

  @Autowired
  ContactInfoRepository repository;

  @Override
  public BaseRepository<ContactInfoEntity> getBaseRepository() {
    return this.repository;
  }
}
