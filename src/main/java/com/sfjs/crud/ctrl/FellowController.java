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

import com.sfjs.data.entity.FellowEntity;
import com.sfjs.crud.svc.FellowService;
import com.sfjs.data.api.FellowData;

@RestController
@EnableWebMvc
@Transactional
public class FellowController extends BaseController<FellowService, FellowEntity, FellowData> {

  @MutationMapping(name = "deleteFellow")
  public Boolean deleteFellow(@Argument(name = "id") Long id) {
    return delete(id);
  }

  @RequestMapping(path = "/fellow/{id}", method = RequestMethod.DELETE)
  public Boolean delete(@PathVariable(name = "id") Long id) {
    return super.delete(id);
  }

  @RequestMapping(path = "/fellow/getbyid/{id}", method = RequestMethod.GET)
  public FellowData getById(@PathVariable("id") Long id) {
    return super.getById(id);
  }

  @RequestMapping(path = "/fellow/findbyid/{id}", method = RequestMethod.GET)
  public FellowData findById(@PathVariable("id") Long id) {
    return super.findById(id);
  }

  @RequestMapping(path = "/fellow/findallbyid/{id}", method = RequestMethod.GET)
  public List<FellowData> findAllById(@PathVariable("id") Long id) {
    return super.findAllById(id);
  }

  @RequestMapping(path = "/fellow/findbyname/{name}", method = RequestMethod.GET)
  public FellowData findByName(@PathVariable("name") String name) {
    return super.findByName(name);
  }

  @RequestMapping(path = "/fellow/findallbyname/{name}", method = RequestMethod.GET)
  public List<FellowData> findAllByName(@PathVariable("name") String name) {
    return super.findAllByName(name);
  }

  @RequestMapping(path = "/fellow/findbylabel/{label}", method = RequestMethod.GET)
  public FellowData findByLabel(@PathVariable("label") String label) {
    return super.findByLabel(label);
  }

  @RequestMapping(path = "/fellow/findallbylabel/{label}", method = RequestMethod.GET)
  public List<FellowData> findAllByLabel(@PathVariable("label") String label) {
    return super.findAllByLabel(label);
  }

  @QueryMapping(name = "findAllFellows")
  @RequestMapping(path = "/fellow/findall", method = RequestMethod.GET)
  public List<FellowData> findAll(@Argument(name = "limit") Integer limit) {
    return super.findAll(limit);
  }
}
