package com.sfjs.crud.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.conv.BaseConverter;
import com.sfjs.crud.entity.AddressEntity;
import com.sfjs.crud.repo.BaseRepository;
import com.sfjs.crud.response.AddressResponse;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AddressService extends BaseService<AddressEntity, AddressResponse> {

  @Autowired
  BaseRepository<AddressEntity> repository;

  public AddressService() {
    super(new BaseConverter<AddressEntity, AddressResponse>(AddressResponse.class));
  }

  @Override
  public BaseRepository<AddressEntity> getBaseRepository() {
    return this.repository;
  }
}
