package com.sfjs.svc;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

  @Autowired
  PasswordEncoder passwordEncoder;

  @Override
  public BaseRepository<AccountEntity> getBaseRepository() {
    return this.repository;
  }

  @Override
  public AccountEntity save(AccountEntity entity) {
    if (entity.getPassword() != null) {
      String rawPassword = entity.getPassword();
      String encryptedPassword = passwordEncoder.encode(rawPassword);
      logger.info("Encrypted password: " + encryptedPassword);
      entity.setPassword(encryptedPassword);
    }
    if (entity.getRoles() != null) {
      Set<RoleEntity> roles = entity.getRoles().stream().map(role -> {
        return roleRepository.findById(role.getId()).orElseThrow(); // Need to load the role
      }).collect(Collectors.toSet());
      entity.setRoles(roles);
    } else {
      entity.setRoles(Collections.emptySet());
    }
    if (entity.getId() == null) {
      AccountEntity accountEntity = this.findByEmail(entity.getEmail());
      return accountEntity;
    }
    return super.save(entity);
  }

  public AccountEntity findByEmail(String email) {
    AccountEntity entity = repository.findByEmail(email);
    return entity;
  }
}
