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

import com.sfjs.dto.BusinessRequest;
import com.sfjs.dto.response.BusinessResponse;
import com.sfjs.entity.BusinessEntity;
import com.sfjs.svc.BusinessService;

@RestController
@EnableWebMvc
@Transactional
public class BusinessController extends BaseController<BusinessService, BusinessEntity, BusinessRequest, BusinessResponse> {

  @MutationMapping(name = "deleteBusiness")
  public Boolean deleteBusiness(@Argument(name = "id") Long id) {
    return delete(id);
  }

  @RequestMapping(path = "/business/{id}", method = RequestMethod.DELETE)
  public Boolean delete(@PathVariable(name = "id") Long id) {
    return super.delete(id);
  }

  @MutationMapping(name = "saveBusiness")
  public BusinessResponse saveBusiness(@Argument(name = "requestBody") BusinessRequest requestBody) {
    return save(requestBody);
  }

  @RequestMapping(path = "/business", method = RequestMethod.POST)
  public BusinessResponse save(@RequestBody BusinessRequest requestBody) {
    return super.save(requestBody);
  }

  @RequestMapping(path = "/business/getbyid/{id}", method = RequestMethod.GET)
  public BusinessResponse getById(@PathVariable("id") Long id) {
    return super.getById(id);
  }

  @RequestMapping(path = "/business/findbyid/{id}", method = RequestMethod.GET)
  public BusinessResponse findById(@PathVariable("id") Long id) {
    return super.findById(id);
  }

  @RequestMapping(path = "/business/findallbyid/{id}", method = RequestMethod.GET)
  public List<BusinessResponse> findAllById(@PathVariable("id") Long id) {
    return super.findAllById(id);
  }

  @RequestMapping(path = "/business/findbyname/{name}", method = RequestMethod.GET)
  public BusinessResponse findByName(@PathVariable("name") String name) {
    return super.findByName(name);
  }

  @RequestMapping(path = "/business/findallbyname/{name}", method = RequestMethod.GET)
  public List<BusinessResponse> findAllByName(@PathVariable("name") String name) {
    return super.findAllByName(name);
  }

  @RequestMapping(path = "/business/findbylabel/{label}", method = RequestMethod.GET)
  public BusinessResponse findByLabel(@PathVariable("label") String label) {
    return super.findByLabel(label);
  }

  @RequestMapping(path = "/business/findallbylabel/{label}", method = RequestMethod.GET)
  public List<BusinessResponse> findAllByLabel(@PathVariable("label") String label) {
    return super.findAllByLabel(label);
  }

  @QueryMapping(name = "findAllBusinesses")
  @RequestMapping(path = "/business/findall", method = RequestMethod.GET)
  public List<BusinessResponse> findAll(@Argument(name = "limit") Integer limit) {
    return super.findAll(limit);
  }
}
