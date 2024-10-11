package com.sfjs.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.conv.BaseConverter;
import com.sfjs.dto.request.AddressRequest;
import com.sfjs.dto.response.AddressResponse;
import com.sfjs.entity.AddressEntity;
import com.sfjs.repo.BaseRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AddressService extends BaseService<AddressEntity, AddressRequest, AddressResponse> {

  @Autowired
  BaseRepository<AddressEntity> repository;

  public AddressService() {
    super(new BaseConverter<AddressEntity, AddressRequest, AddressResponse>(AddressEntity.class, AddressResponse.class));
  }

  @Override
  public BaseRepository<AddressEntity> getBaseRepository() {
    return this.repository;
  }
}
