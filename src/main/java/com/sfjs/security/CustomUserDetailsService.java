package com.sfjs.security;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sfjs.data.core.AccountAuthorizationPrincipal;
import com.sfjs.data.entity.AccountEntity;
import com.sfjs.jpa.repo.AccountRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private AccountRepository accountRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AccountEntity accountEntity = accountRepository.findByEmail(username);
    AccountAuthorizationPrincipal result = new AccountAuthorizationPrincipal();
    result.setUsername(username);
    result.setPassword(accountEntity.getPassword());
    result.setAuthorities(accountEntity.getRoles().stream().map(role -> {
      return new SimpleGrantedAuthority("ROLE_" + role.getName());
    }).collect(Collectors.toList()));
    return result;
  }

}
