package com.sfjs.crud.ctrl;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.sfjs.jpa.entity.AddressEntity;
import com.sfjs.crud.svc.AddressService;
import com.sfjs.data.AddressData;

@RestController
@EnableWebMvc
@Transactional
public class AddressController extends BaseController<AddressService, AddressEntity, AddressData> {

  @MutationMapping(name = "deleteAddress")
  public Boolean deleteAddress(@Argument(name = "id") Long id) {
    return delete(id);
  }

  @RequestMapping(path = "/address/{id}", method = RequestMethod.DELETE)
  public Boolean delete(@PathVariable(name = "id") Long id) {
    return super.delete(id);
  }

  @RequestMapping(path = "/address/getbyid/{id}", method = RequestMethod.GET)
  public AddressData getById(@PathVariable("id") Long id) {
    return super.getById(id);
  }

  @RequestMapping(path = "/address/findbyid/{id}", method = RequestMethod.GET)
  public AddressData findById(@PathVariable("id") Long id) {
    return super.findById(id);
  }

  @RequestMapping(path = "/address/findallbyid/{id}", method = RequestMethod.GET)
  public List<AddressData> findAllById(@PathVariable("id") Long id) {
    return super.findAllById(id);
  }

  @RequestMapping(path = "/address/findbyname/{name}", method = RequestMethod.GET)
  public AddressData findByName(@PathVariable("name") String name) {
    return super.findByName(name);
  }

  @RequestMapping(path = "/address/findallbyname/{name}", method = RequestMethod.GET)
  public List<AddressData> findAllByName(@PathVariable("name") String name) {
    return super.findAllByName(name);
  }

  @RequestMapping(path = "/address/findbylabel/{label}", method = RequestMethod.GET)
  public AddressData findByLabel(@PathVariable("label") String label) {
    return super.findByLabel(label);
  }

  @RequestMapping(path = "/address/findallbylabel/{label}", method = RequestMethod.GET)
  public List<AddressData> findAllByLabel(@PathVariable("label") String label) {
    return super.findAllByLabel(label);
  }

  @QueryMapping(name = "findAllAddresses")
  @RequestMapping(path = "/address/findall", method = RequestMethod.GET)
  public List<AddressData> findAll(@Argument(name = "limit") Integer limit) {
    return super.findAll(limit);
  }
}
