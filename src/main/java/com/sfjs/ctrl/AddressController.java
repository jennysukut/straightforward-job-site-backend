package com.sfjs.ctrl;

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

import com.sfjs.dto.Address;
import com.sfjs.entity.AddressEntity;
import com.sfjs.svc.AddressService;

@RestController
@EnableWebMvc
@Transactional
public class AddressController extends BaseController<AddressService, AddressEntity, Address> {

  @MutationMapping(name = "deleteAddress")
  public Boolean deleteAddress(@Argument(name = "id") Long id) {
    return delete(id);
  }

  @RequestMapping(path = "/address/{id}", method = RequestMethod.DELETE)
  public Boolean delete(@PathVariable(name = "id") Long id) {
    return super.delete(id);
  }

  @MutationMapping(name = "saveAddress")
  public Address saveAddress(@Argument(name = "requestBody") Address requestBody) {
    return save(requestBody);
  }

  @RequestMapping(path = "/address", method = RequestMethod.POST)
  public Address save(@RequestBody Address requestBody) {
    return super.save(requestBody);
  }

  @RequestMapping(path = "/address/getbyid/{id}", method = RequestMethod.GET)
  public Address getById(@PathVariable("id") Long id) {
    return super.getById(id);
  }

  @RequestMapping(path = "/address/findbyid/{id}", method = RequestMethod.GET)
  public Address findById(@PathVariable("id") Long id) {
    return super.findById(id);
  }

  @RequestMapping(path = "/address/findallbyid/{id}", method = RequestMethod.GET)
  public List<Address> findAllById(@PathVariable("id") Long id) {
    return super.findAllById(id);
  }

  @RequestMapping(path = "/address/findbyname/{name}", method = RequestMethod.GET)
  public Address findByName(@PathVariable("name") String name) {
    return super.findByName(name);
  }

  @RequestMapping(path = "/address/findallbyname/{name}", method = RequestMethod.GET)
  public List<Address> findAllByName(@PathVariable("name") String name) {
    return super.findAllByName(name);
  }

  @RequestMapping(path = "/address/findbylabel/{label}", method = RequestMethod.GET)
  public Address findByLabel(@PathVariable("label") String label) {
    return super.findByLabel(label);
  }

  @RequestMapping(path = "/address/findallbylabel/{label}", method = RequestMethod.GET)
  public List<Address> findAllByLabel(@PathVariable("label") String label) {
    return super.findAllByLabel(label);
  }

  @QueryMapping(name = "findAllAddresses")
  @RequestMapping(path = "/address/findall", method = RequestMethod.GET)
  public List<Address> findAll(@Argument(name = "limit") Integer limit) {
    return super.findAll(limit);
  }
}
