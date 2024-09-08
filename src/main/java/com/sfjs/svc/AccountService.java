package com.sfjs.svc;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.entity.AccountEntity;
import com.sfjs.entity.RoleEntity;
import com.sfjs.repo.AccountRepository;
import com.sfjs.repo.BaseRepository;
import com.sfjs.repo.RoleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AccountService extends BaseService<AccountEntity> {

  @Autowired
  AccountRepository repository;

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public BaseRepository<AccountEntity> getBaseRepository() {
    return this.repository;
  }

  @Override
  public AccountEntity save(AccountEntity entity) {
    if (entity.getRoles() != null) {
      Set<RoleEntity> roles = entity.getRoles().stream().map(role -> {
        return roleRepository.findById(role.getId()).orElseThrow(); // Need to load the role
      }).collect(Collectors.toSet());
      entity.setRoles(roles);
    } else {
      entity.setRoles(Collections.emptySet());
    }
    return super.save(entity);
  }
}
