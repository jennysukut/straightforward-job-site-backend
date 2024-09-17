package com.sfjs.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.conv.BaseConverter;
import com.sfjs.dto.Address;
import com.sfjs.entity.AddressEntity;
import com.sfjs.persist.AddressPersist;
import com.sfjs.persist.BasePersist;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AddressService extends BaseService<AddressEntity, Address> {

  @Autowired
  AddressPersist repository;

  public AddressService() {
    super(new BaseConverter<AddressEntity, Address>(AddressEntity.class, Address.class));
  }
  
  @Override
  public BasePersist<AddressEntity> getBaseRepository() {
    return this.repository;
  }
}
