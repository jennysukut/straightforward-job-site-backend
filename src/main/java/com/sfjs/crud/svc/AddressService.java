package com.sfjs.crud.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.conv.BaseConverter;
import com.sfjs.data.AddressData;
import com.sfjs.jpa.entity.AddressEntity;
import com.sfjs.jpa.repo.BaseRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AddressService extends BaseService<AddressEntity, AddressData> {

  @Autowired
  BaseRepository<AddressEntity> repository;

  public AddressService() {
    super(new BaseConverter<AddressEntity, AddressData>(AddressData.class));
  }

  @Override
  public BaseRepository<AddressEntity> getBaseRepository() {
    return this.repository;
  }
}
