package com.sfjs.gql.svc;

import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.data.core.Role;
import com.sfjs.data.entity.AccountEntity;
import com.sfjs.data.entity.RoleEntity;
import com.sfjs.jpa.repo.AccountRepository;
import com.sfjs.jpa.repo.RoleRepository;

@Service
@Transactional
public class AccountService {

  Logger logger = Logger.getLogger(getClass().getName());

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public Optional<AccountEntity> createNewBusinessAccount(String email, String password) {
    return createNewAccount(email, password, Role.BUSINESS);
  }

  public Optional<AccountEntity> createNewFellowAccount(String email, String password) {
    return createNewAccount(email, password, Role.FELLOW);
  }

  private Optional<AccountEntity> createNewAccount(String email, String password, String role) {
    logger.info("This email has never been used");
    AccountEntity newAccountEntity = new AccountEntity();
    newAccountEntity.setEmail(email);
    if (password != null) {
      newAccountEntity.setPassword(passwordEncoder.encode(password));
    }
    newAccountEntity.setEnabled(true);
    RoleEntity roleEntity = roleRepository.findByName(role);
    newAccountEntity.setRoles(Set.of(roleEntity));
    return Optional.of(accountRepository.save(newAccountEntity));
  }
}
