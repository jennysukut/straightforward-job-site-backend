package com.sfjs.svc;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.entity.AccountEntity;
import com.sfjs.entity.BusinessEntity;
import com.sfjs.entity.ContactInfoEntity;
import com.sfjs.entity.RoleEntity;
import com.sfjs.repo.BaseRepository;
import com.sfjs.repo.BusinessRepository;
import com.sfjs.repo.ContactInfoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BusinessService extends BaseService<BusinessEntity> {

  @Autowired
  BusinessRepository repository;

  @Autowired
  AccountService accountService;

  @Autowired
  RoleService roleService;

  @Autowired
  ContactInfoRepository contactInfoRepository;

  @Override
  public BaseRepository<BusinessEntity> getBaseRepository() {
    return this.repository;
  }

  @Override
  public BusinessEntity save(BusinessEntity entity) {
    if (entity.getAccount() != null) {
      AccountEntity accountEntity = entity.getAccount();
      if (accountEntity.getId() == null) {
        Set<RoleEntity> roleEntities = accountEntity.getRoles().stream().map(roleEntity -> {
          return roleEntity.getId() != null ? roleEntity : roleService.findByName(roleEntity.getName());
        }).collect(Collectors.toSet());
        accountEntity.setRoles(roleEntities);
        accountEntity = accountService.save(accountEntity);
        entity.setAccount(accountEntity);
      }
    } else {
      throw new IllegalArgumentException("Account can not be null");
    }
    if (entity.getContactInfo() != null) {
      ContactInfoEntity contactInfoEntity = entity.getContactInfo();
      Long contactInfoId = contactInfoEntity.getId();
      if (contactInfoId == null) {
        contactInfoEntity = contactInfoRepository.save(contactInfoEntity);
        entity.setContactInfo(contactInfoEntity);
      }
    } else {
      throw new IllegalArgumentException("ContactInfo can not be null");
    }
    return super.save(entity);
  }
}
