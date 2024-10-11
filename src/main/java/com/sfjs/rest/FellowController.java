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

import com.sfjs.dto.FellowRequest;
import com.sfjs.dto.response.FellowResponse;
import com.sfjs.entity.FellowEntity;
import com.sfjs.svc.FellowService;

@RestController
@EnableWebMvc
@Transactional
public class FellowController extends BaseController<FellowService, FellowEntity, FellowRequest, FellowResponse> {

  @MutationMapping(name = "deleteFellow")
  public Boolean deleteFellow(@Argument(name = "id") Long id) {
    return delete(id);
  }

  @RequestMapping(path = "/fellow/{id}", method = RequestMethod.DELETE)
  public Boolean delete(@PathVariable(name = "id") Long id) {
    return super.delete(id);
  }

  @MutationMapping(name = "saveFellow")
  public FellowResponse saveFellow(@Argument(name = "requestBody") FellowRequest requestBody) {
    return save(requestBody);
  }

  @RequestMapping(path = "/fellow", method = RequestMethod.POST)
  public FellowResponse save(@RequestBody FellowRequest requestBody) {
    return super.save(requestBody);
  }

  @RequestMapping(path = "/fellow/getbyid/{id}", method = RequestMethod.GET)
  public FellowResponse getById(@PathVariable("id") Long id) {
    return super.getById(id);
  }

  @RequestMapping(path = "/fellow/findbyid/{id}", method = RequestMethod.GET)
  public FellowResponse findById(@PathVariable("id") Long id) {
    return super.findById(id);
  }

  @RequestMapping(path = "/fellow/findallbyid/{id}", method = RequestMethod.GET)
  public List<FellowResponse> findAllById(@PathVariable("id") Long id) {
    return super.findAllById(id);
  }

  @RequestMapping(path = "/fellow/findbyname/{name}", method = RequestMethod.GET)
  public FellowResponse findByName(@PathVariable("name") String name) {
    return super.findByName(name);
  }

  @RequestMapping(path = "/fellow/findallbyname/{name}", method = RequestMethod.GET)
  public List<FellowResponse> findAllByName(@PathVariable("name") String name) {
    return super.findAllByName(name);
  }

  @RequestMapping(path = "/fellow/findbylabel/{label}", method = RequestMethod.GET)
  public FellowResponse findByLabel(@PathVariable("label") String label) {
    return super.findByLabel(label);
  }

  @RequestMapping(path = "/fellow/findallbylabel/{label}", method = RequestMethod.GET)
  public List<FellowResponse> findAllByLabel(@PathVariable("label") String label) {
    return super.findAllByLabel(label);
  }

  @QueryMapping(name = "findAllFellows")
  @RequestMapping(path = "/fellow/findall", method = RequestMethod.GET)
  public List<FellowResponse> findAll(@Argument(name = "limit") Integer limit) {
    return super.findAll(limit);
  }
}
