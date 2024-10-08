package com.sfjs.rest;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.sfjs.dto.Account;
import com.sfjs.entity.AccountEntity;
import com.sfjs.svc.AccountService;

@RestController
@EnableWebMvc
@Transactional
public class AccountController extends BaseController<AccountService, AccountEntity, Account> {

  @MutationMapping(name = "deleteAccount")
  public Boolean deleteAccount(@Argument(name = "id") Long id) {
    return delete(id);
  }

  @RequestMapping(path = "/account/{id}", method = RequestMethod.DELETE)
  public Boolean delete(@PathVariable(name = "id") Long id) {
    return super.delete(id);
  }

  @MutationMapping(name = "saveAccount")
  public Account saveAccount(@Argument(name = "requestBody") Account requestBody) {
    return save(requestBody);
  }

  @RequestMapping(path = "/account", method = RequestMethod.POST)
  public Account save(@RequestBody Account requestBody) {
    return super.save(requestBody);
  }

  @RequestMapping(path = "/account/getbyid/{id}", method = RequestMethod.GET)
  public Account getById(@PathVariable("id") Long id) {
    return super.getById(id);
  }

  @RequestMapping(path = "/account/findbyid/{id}", method = RequestMethod.GET)
  public Account findById(@PathVariable("id") Long id) {
    return super.findById(id);
  }

  @RequestMapping(path = "/account/findallbyid/{id}", method = RequestMethod.GET)
  public List<Account> findAllById(@PathVariable("id") Long id) {
    return super.findAllById(id);
  }

  @RequestMapping(path = "/account/findbyname/{name}", method = RequestMethod.GET)
  public Account findByName(@PathVariable("name") String name) {
    return super.findByName(name);
  }

  @RequestMapping(path = "/account/findallbyname/{name}", method = RequestMethod.GET)
  public List<Account> findAllByName(@PathVariable("name") String name) {
    return super.findAllByName(name);
  }

  @RequestMapping(path = "/account/findbylabel/{label}", method = RequestMethod.GET)
  public Account findByLabel(@PathVariable("label") String label) {
    return super.findByLabel(label);
  }

  @RequestMapping(path = "/account/findallbylabel/{label}", method = RequestMethod.GET)
  public List<Account> findAllByLabel(@PathVariable("label") String label) {
    return super.findAllByLabel(label);
  }

  @QueryMapping(name = "findAllAccounts")
  @RequestMapping(path = "/account/findall", method = RequestMethod.GET)
  public List<Account> findAll(@Argument(name = "limit") Integer limit) {
    return super.findAll(limit);
  }
}
