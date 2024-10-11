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

import com.sfjs.dto.AddressRequest;
import com.sfjs.dto.response.AddressResponse;
import com.sfjs.entity.AddressEntity;
import com.sfjs.svc.AddressService;

@RestController
@EnableWebMvc
@Transactional
public class AddressController extends BaseController<AddressService, AddressEntity, AddressRequest, AddressResponse> {

  @MutationMapping(name = "deleteAddress")
  public Boolean deleteAddress(@Argument(name = "id") Long id) {
    return delete(id);
  }

  @RequestMapping(path = "/address/{id}", method = RequestMethod.DELETE)
  public Boolean delete(@PathVariable(name = "id") Long id) {
    return super.delete(id);
  }

  @MutationMapping(name = "saveAddress")
  public AddressResponse saveAddress(@Argument(name = "requestBody") AddressRequest requestBody) {
    return save(requestBody);
  }

  @RequestMapping(path = "/address", method = RequestMethod.POST)
  public AddressResponse save(@RequestBody AddressRequest requestBody) {
    return super.save(requestBody);
  }

  @RequestMapping(path = "/address/getbyid/{id}", method = RequestMethod.GET)
  public AddressResponse getById(@PathVariable("id") Long id) {
    return super.getById(id);
  }

  @RequestMapping(path = "/address/findbyid/{id}", method = RequestMethod.GET)
  public AddressResponse findById(@PathVariable("id") Long id) {
    return super.findById(id);
  }

  @RequestMapping(path = "/address/findallbyid/{id}", method = RequestMethod.GET)
  public List<AddressResponse> findAllById(@PathVariable("id") Long id) {
    return super.findAllById(id);
  }

  @RequestMapping(path = "/address/findbyname/{name}", method = RequestMethod.GET)
  public AddressResponse findByName(@PathVariable("name") String name) {
    return super.findByName(name);
  }

  @RequestMapping(path = "/address/findallbyname/{name}", method = RequestMethod.GET)
  public List<AddressResponse> findAllByName(@PathVariable("name") String name) {
    return super.findAllByName(name);
  }

  @RequestMapping(path = "/address/findbylabel/{label}", method = RequestMethod.GET)
  public AddressResponse findByLabel(@PathVariable("label") String label) {
    return super.findByLabel(label);
  }

  @RequestMapping(path = "/address/findallbylabel/{label}", method = RequestMethod.GET)
  public List<AddressResponse> findAllByLabel(@PathVariable("label") String label) {
    return super.findAllByLabel(label);
  }

  @QueryMapping(name = "findAllAddresses")
  @RequestMapping(path = "/address/findall", method = RequestMethod.GET)
  public List<AddressResponse> findAll(@Argument(name = "limit") Integer limit) {
    return super.findAll(limit);
  }
}
