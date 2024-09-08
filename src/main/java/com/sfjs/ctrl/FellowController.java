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

import com.sfjs.dto.Fellow;
import com.sfjs.entity.FellowEntity;
import com.sfjs.svc.FellowService;

@RestController
@EnableWebMvc
@Transactional
public class FellowController extends BaseController<FellowService, FellowEntity, Fellow> {

  @MutationMapping(name = "deleteFellow")
  public Boolean deleteFellow(@Argument(name = "id") Long id) {
    return delete(id);
  }

  @RequestMapping(path = "/fellow/{id}", method = RequestMethod.DELETE)
  public Boolean delete(@PathVariable(name = "id") Long id) {
    return super.delete(id);
  }

  @MutationMapping(name = "saveFellow")
  public Fellow saveFellow(@Argument(name = "requestBody") Fellow requestBody) {
    return save(requestBody);
  }

  @RequestMapping(path = "/fellow", method = RequestMethod.POST)
  public Fellow save(@RequestBody Fellow requestBody) {
    return super.save(requestBody);
  }

  @RequestMapping(path = "/fellow/getbyid/{id}", method = RequestMethod.GET)
  public Fellow getById(@PathVariable("id") Long id) {
    return super.getById(id);
  }

  @RequestMapping(path = "/fellow/findbyid/{id}", method = RequestMethod.GET)
  public Fellow findById(@PathVariable("id") Long id) {
    return super.findById(id);
  }

  @RequestMapping(path = "/fellow/findallbyid/{id}", method = RequestMethod.GET)
  public List<Fellow> findAllById(@PathVariable("id") Long id) {
    return super.findAllById(id);
  }

  @RequestMapping(path = "/fellow/findbyname/{name}", method = RequestMethod.GET)
  public Fellow findByName(@PathVariable("name") String name) {
    return super.findByName(name);
  }

  @RequestMapping(path = "/fellow/findallbyname/{name}", method = RequestMethod.GET)
  public List<Fellow> findAllByName(@PathVariable("name") String name) {
    return super.findAllByName(name);
  }

  @RequestMapping(path = "/fellow/findbylabel/{label}", method = RequestMethod.GET)
  public Fellow findByLabel(@PathVariable("label") String label) {
    return super.findByLabel(label);
  }

  @RequestMapping(path = "/fellow/findallbylabel/{label}", method = RequestMethod.GET)
  public List<Fellow> findAllByLabel(@PathVariable("label") String label) {
    return super.findAllByLabel(label);
  }

  @QueryMapping(name = "findAllFellows")
  @RequestMapping(path = "/fellow/findall", method = RequestMethod.GET)
  public List<Fellow> findAll(@Argument Integer limit) {
    return super.findAll(limit);
  }
}
