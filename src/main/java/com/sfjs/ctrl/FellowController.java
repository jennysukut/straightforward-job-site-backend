package com.sfjs.ctrl;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sfjs.dto.Fellow;
import com.sfjs.entity.FellowEntity;
import com.sfjs.svc.FellowService;

@RestController
@EnableWebMvc
@Transactional
public class FellowController {

  @Autowired
  FellowService service;
  Logger logger = Logger.getLogger(FellowController.class.getName());
  ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

  @RequestMapping(path = "/fellow/{id}", method = RequestMethod.DELETE)
  public void delete(@PathVariable(name = "id") Long id) {
    service.delete(id);
  }

  @RequestMapping(path = "/fellow", method = RequestMethod.POST)
  public FellowEntity save(@RequestBody Fellow requestBody) {
    logger.info("Request body: " + requestBody);
    FellowEntity fellowEntity = new FellowEntity();
    fellowEntity.setId(requestBody.getId());
    fellowEntity.setName(requestBody.getName());
    fellowEntity.setLabel(requestBody.getLabel());
    fellowEntity.setFirstName(requestBody.getFirstName());
    fellowEntity.setLastName(requestBody.getLastName());
    fellowEntity.setEmail(requestBody.getEmail());
    fellowEntity.setPassword(requestBody.getPassword());
    return service.save(fellowEntity);
  }

  @RequestMapping(path = "/fellow/getbyid/{id}", method = RequestMethod.GET)
  public Fellow getById(@PathVariable("id") Long id) {
    logger.info("Path variable: " + id);
    FellowEntity fellowEntity = service.getById(id);
    Fellow fellow = new Fellow(fellowEntity);
    return fellow;
  }

  @RequestMapping(path = "/fellow/findbyid/{id}", method = RequestMethod.GET)
  public FellowEntity findById(@PathVariable("id") Long id) {
    logger.info("Path variable: " + id);
    return service.findById(id);
  }

  @RequestMapping(path = "/fellow/findallbyid/{id}", method = RequestMethod.GET)
  public List<FellowEntity> findAllById(@PathVariable("id") Long id) {
    logger.info("Path variable: " + id);
    return service.findAllById(id);
  }

  @RequestMapping(path = "/fellow/findbyname/{name}", method = RequestMethod.GET)
  public FellowEntity findByName(@PathVariable("name") String name) {
    logger.info("Path variable: " + name);
    return service.findByName(name);
  }

  @RequestMapping(path = "/fellow/findallbyname/{name}", method = RequestMethod.GET)
  public List<FellowEntity> findAllByName(@PathVariable("name") String name) {
    logger.info("Path variable: " + name);
    return service.findAllByName(name);
  }

  @RequestMapping(path = "/fellow/findbylabel/{label}", method = RequestMethod.GET)
  public FellowEntity findByLabel(@PathVariable("label") String label) {
    logger.info("Path variable: " + label);
    return service.findByLabel(label);
  }

  @RequestMapping(path = "/fellow/findallbylabel/{label}", method = RequestMethod.GET)
  public List<FellowEntity> findAllByLabel(@PathVariable("label") String label) {
    logger.info("Path variable: " + label);
    return service.findAllByLabel(label);
  }

  @RequestMapping(path = "/fellow/findall", method = RequestMethod.GET)
  public List<FellowEntity> findAll(@RequestParam("limit") Optional<Integer> limit, Principal principal) {
    logger.info("Request param limit: " + limit);
    return service.findAll(limit).getContent();
  }
}
