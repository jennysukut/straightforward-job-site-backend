package com.sfjs.data.core;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

public class AccountAuthorizationPrincipal implements UserDetails {

  private static final long serialVersionUID = 1L;

  @Getter @Setter private String username;
  @Getter @Setter private String password;
  @Getter @Setter private Collection<? extends GrantedAuthority> authorities;
}
