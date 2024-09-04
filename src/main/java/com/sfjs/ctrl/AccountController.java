package com.sfjs.ctrl;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sfjs.dto.Account;
import com.sfjs.entity.AccountEntity;
import com.sfjs.entity.RoleEntity;
import com.sfjs.svc.AccountService;

@RestController
@EnableWebMvc
@Transactional
public class AccountController {

  @Autowired
  AccountService service;

  Logger logger = Logger.getLogger(AccountController.class.getName());
  ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

  @Autowired
  PasswordEncoder passwordEncoder;

  @RequestMapping(path = "/account/{id}", method = RequestMethod.DELETE)
  public void delete(@PathVariable(name = "id") Long id) {
    service.delete(id);
  }

  @MutationMapping(name = "saveAccount")
  @RequestMapping(path = "/account", method = RequestMethod.POST)
  public Account save(@Argument Long id, @Argument String name, @Argument String label, @Argument String password,
      @Argument Boolean enabled, @Argument List<Long> roles) {
    try {
      AccountEntity accountEntity;
      if (id == null) {
        accountEntity = new AccountEntity();
      } else {
        accountEntity = service.getById(id);
      }
      accountEntity.setName(name);
      accountEntity.setLabel(label);
      String rawPassword = password;
      String encryptedPassword = passwordEncoder.encode(rawPassword);
      logger.info("Encrypted password: " + encryptedPassword);
      accountEntity.setPassword(encryptedPassword);
      accountEntity.setEnabled(enabled);
      if (roles != null) {
        accountEntity.setRoles(roles.stream().map(roleId -> {
          RoleEntity roleEntity = new RoleEntity();
          roleEntity.setId(roleId);
          return roleEntity;
        }).collect(Collectors.toSet()));
      }
      return new Account(service.save(accountEntity));
    } catch (Exception ex) {
      logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
      throw ex;
    }
  }

  @RequestMapping(path = "/account/getbyid/{id}", method = RequestMethod.GET)
  public Account getById(@PathVariable("id") Long id) {
    logger.info("Path variable: " + id);
    AccountEntity accountEntity = service.getById(id);
    Account account = new Account(accountEntity);
    return account;
  }

  @RequestMapping(path = "/account/findbyid/{id}", method = RequestMethod.GET)
  public Account findById(@PathVariable("id") Long id) {
    logger.info("Path variable: " + id);
    return new Account(service.findById(id));
  }

  @RequestMapping(path = "/account/findallbyid/{id}", method = RequestMethod.GET)
  public List<Account> findAllById(@PathVariable("id") Long id) {
    logger.info("Path variable: " + id);
    return service.findAllById(id).stream().map(accountEntity -> {
      return new Account(accountEntity);
    }).collect(Collectors.toList());
  }

  @RequestMapping(path = "/account/findbyname/{name}", method = RequestMethod.GET)
  public Account findByName(@PathVariable("name") String name) {
    logger.info("Path variable: " + name);
    return new Account(service.findByName(name));
  }

  @RequestMapping(path = "/account/findallbyname/{name}", method = RequestMethod.GET)
  public List<Account> findAllByName(@PathVariable("name") String name) {
    logger.info("Path variable: " + name);
    return service.findAllByName(name).stream().map(accountEntity -> {
      return new Account(accountEntity);
    }).collect(Collectors.toList());
  }

  @RequestMapping(path = "/account/findbylabel/{label}", method = RequestMethod.GET)
  public Account findByLabel(@PathVariable("label") String label) {
    logger.info("Path variable: " + label);
    return new Account(service.findByLabel(label));
  }

  @RequestMapping(path = "/account/findallbylabel/{label}", method = RequestMethod.GET)
  public List<Account> findAllByLabel(@PathVariable("label") String label) {
    logger.info("Path variable: " + label);
    return service.findAllByLabel(label).stream().map(accountEntity -> {
      return new Account(accountEntity);
    }).collect(Collectors.toList());
  }

  @RequestMapping(path = "/account/findall", method = RequestMethod.GET)
  public List<Account> findAll(@RequestParam("limit") Optional<Integer> limit, Principal principal) {
    logger.info("Request param limit: " + limit);
    return service.findAll().stream().map(accountEntity -> {
      return new Account(accountEntity);
    }).collect(Collectors.toList());
  }
}
