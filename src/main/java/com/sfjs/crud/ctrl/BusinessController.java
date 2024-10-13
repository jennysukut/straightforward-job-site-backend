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

import com.sfjs.crud.entity.BusinessEntity;
import com.sfjs.crud.response.BusinessResponse;
import com.sfjs.crud.svc.BusinessService;

@RestController
@EnableWebMvc
@Transactional
public class BusinessController extends BaseController<BusinessService, BusinessEntity, BusinessResponse> {

  @MutationMapping(name = "deleteBusiness")
  public Boolean deleteBusiness(@Argument(name = "id") Long id) {
    return delete(id);
  }

  @RequestMapping(path = "/business/{id}", method = RequestMethod.DELETE)
  public Boolean delete(@PathVariable(name = "id") Long id) {
    return super.delete(id);
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
