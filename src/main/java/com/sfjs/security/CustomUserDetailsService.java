package com.sfjs.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sfjs.jpa.entity.AccountEntity;
import com.sfjs.crud.repo.AccountRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private AccountRepository accountRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AccountEntity accountEntity = accountRepository.findByEmail(username);
    UserDetails result = new UserDetails() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getUsername() {
        return accountEntity.getEmail();
      }

      @Override
      public String getPassword() {
        return accountEntity.getPassword();
      }

      @Override
      public Collection<? extends GrantedAuthority> getAuthorities() {
        return accountEntity.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
            .collect(Collectors.toList());
      }
    };
    return result;
  }
}
