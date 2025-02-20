package com.sfjs.security;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sfjs.data.entity.AccountEntity;
import com.sfjs.data.entity.ResetPasswordTokenEntity;
import com.sfjs.jpa.repo.AccountRepository;
import com.sfjs.jpa.repo.ResetPasswordTokenRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthorizationService {

  Logger logger = Logger.getLogger(getClass().getName());

  @Autowired
  AccountRepository accountRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private ResetPasswordTokenRepository resetPasswordTokenRepository;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private HttpServletResponse response;

  public boolean resetPassword(String email, String password, String token) {
//    Result result = new Result();
    AccountEntity accountEntity = accountRepository.findByEmail(email);
    if (accountEntity == null) {
//      result.setSuccess(false);
//      result.setMessage("Account not found");
//      return result;
      throw new IllegalArgumentException("Account not found");
    }
    Optional<ResetPasswordTokenEntity> filteredTokenEntityList = accountEntity.getTokens()
        .stream().filter(new Predicate<ResetPasswordTokenEntity>() {

          @Override
          public boolean test(ResetPasswordTokenEntity t) {
            logger.info("Expires at: " + t.getExpiresAt());
            boolean isExpired = t.getExpiresAt().isBefore(LocalDateTime.now());
            logger.info("Is used: " + t.isUsed());
            boolean matches = passwordEncoder.matches(token, t.getToken());
            logger.info("Matches: " + matches);
            return matches && !t.isUsed() && !isExpired;
          }
        }).findAny();
    if (filteredTokenEntityList.isEmpty()) {
//      result.setSuccess(false);
//      result.setMessage("Token not found");
//      return result;
      throw new IllegalArgumentException("Token not found");
    }
    ResetPasswordTokenEntity tokenEntity = filteredTokenEntityList.get();
    accountEntity.setPassword(passwordEncoder.encode(password));
    accountRepository.save(accountEntity);
    tokenEntity.setUsed(true);
    resetPasswordTokenRepository.save(tokenEntity);
//    result.setSuccess(true);
//    return result;
    return true;
  }

  public boolean login(String email, String password) {
//    Result result = new Result();

    // Create the authentication object
    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(email, password));
    logger.info("Authentication object: " + authentication.getClass().getName());
    Object principal = authentication.getPrincipal();
    if (principal instanceof UserDetails) {
      UserDetails userDetails = (UserDetails) principal;
      logger.info("Authentication principal username: " + userDetails.getUsername());
    }

    if (authentication.isAuthenticated()) {
      // Add the authentication object to the security context
      SecurityContextHolder.getContext().setAuthentication(authentication);
//      result.setSuccess(true);
      // Generate a JWT token for future requests
      String token = jwtTokenUtil.generateToken(email, authentication.getAuthorities());
      response.setHeader("Authorization", "Bearer " + token);
    } else {
//      result.setSuccess(false);
    }
//    return result;
    return true;
  }

  public String generateResetPasswordToken(String email) {
//    Result result = new Result();
    AccountEntity accountEntity = accountRepository.findByEmail(email);
    if (accountEntity == null) {
//      result.setSuccess(false);
//      result.setMessage("Account not found");
//      return result;
      throw new IllegalArgumentException("Account not found");
    }
    ResetPasswordTokenEntity entity = new ResetPasswordTokenEntity();
    entity.setAccount(accountEntity);
    String token = TokenGenerator.generateToken(16);
    String encryptedToken = passwordEncoder.encode(token);
    entity.setToken(encryptedToken);
    LocalDateTime expiresAt = LocalDateTime.now().plusDays(2);
    logger.info("Expires at: " + expiresAt);
    entity.setExpiresAt(expiresAt);
    entity = resetPasswordTokenRepository.save(entity);
//    result.setSuccess(true);
//    result.setMessage(token);
//    return result;
    return token;
  }

  public AccountEntity getAccount() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    logger.info("getAccount: " + authentication);
    Object principal = authentication.getPrincipal();
    logger.info("getAccount: principal: " + principal);
    if (principal instanceof String) {
      String email = principal.toString();
      logger.info("getAccount: email: " + email);
      return accountRepository.findByEmail(email);
    }
    return null;
  }
}
