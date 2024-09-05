package com.sfjs.ctrl;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sfjs.dto.Role;
import com.sfjs.entity.RoleEntity;
import com.sfjs.svc.RoleService;

@RestController
@EnableWebMvc
@Transactional
public class RoleController {

  @Autowired
  RoleService service;

  Logger logger = Logger.getLogger(RoleController.class.getName());
  ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

  @RequestMapping(path = "/role/{id}", method = RequestMethod.DELETE)
  public void delete(@PathVariable(name = "id") Long id) {
    service.delete(id);
  }

  @MutationMapping(name = "saveRole")
  public Role saveRole(@Argument(name = "requestBody") Role requestBody) {
    return save(requestBody);
  }

  @RequestMapping(path = "/role", method = RequestMethod.POST)
  public Role save(@RequestBody Role requestBody) {
    RoleEntity roleEntity;
    if (requestBody.getId() == null) {
      roleEntity = new RoleEntity();
    } else {
      roleEntity = service.getById(requestBody.getId());
    }
    if (requestBody.getName() != null) {
      roleEntity.setName(requestBody.getName());
    }
    if (requestBody.getLabel() != null) {
      roleEntity.setLabel(requestBody.getLabel());
    }
    return new Role(service.save(roleEntity));
  }

  @RequestMapping(path = "/role/getbyid/{id}", method = RequestMethod.GET)
  public Role getById(@PathVariable("id") Long id) {
    logger.info("Path variable: " + id);
    RoleEntity roleEntity = service.getById(id);
    Role role = new Role(roleEntity);
    return role;
  }

  @RequestMapping(path = "/role/findbyid/{id}", method = RequestMethod.GET)
  public Role findById(@PathVariable("id") Long id) {
    logger.info("Path variable: " + id);
    return new Role(service.findById(id));
  }

  @RequestMapping(path = "/role/findallbyid/{id}", method = RequestMethod.GET)
  public List<Role> findAllById(@PathVariable("id") Long id) {
    logger.info("Path variable: " + id);
    return service.findAllById(id).stream().map(roleEntity -> {
      return new Role(roleEntity);
    }).collect(Collectors.toList());
  }

  @RequestMapping(path = "/role/findbyname/{name}", method = RequestMethod.GET)
  public Role findByName(@PathVariable("name") String name) {
    logger.info("Path variable: " + name);
    return new Role(service.findByName(name));
  }

  @RequestMapping(path = "/role/findallbyname/{name}", method = RequestMethod.GET)
  public List<Role> findAllByName(@PathVariable("name") String name) {
    logger.info("Path variable: " + name);
    return service.findAllByName(name).stream().map(roleEntity -> {
      return new Role(roleEntity);
    }).collect(Collectors.toList());
  }

  @RequestMapping(path = "/role/findbylabel/{label}", method = RequestMethod.GET)
  public Role findByLabel(@PathVariable("label") String label) {
    logger.info("Path variable: " + label);
    return new Role(service.findByLabel(label));
  }

  @RequestMapping(path = "/role/findallbylabel/{label}", method = RequestMethod.GET)
  public List<Role> findAllByLabel(@PathVariable("label") String label) {
    logger.info("Path variable: " + label);
    return service.findAllByLabel(label).stream().map(roleEntity -> {
      return new Role(roleEntity);
    }).collect(Collectors.toList());
  }

  @QueryMapping(name = "findAllRoles")
  @RequestMapping(path = "/role/findall", method = RequestMethod.GET)
  public List<Role> findAll(@Argument Integer limit) {
    logger.info("Request param limit: " + limit);
    Stream<RoleEntity> roleStream = service.findAll().stream();
    if (limit != null) {
      roleStream = roleStream.limit(limit);
    }
    return roleStream.map(roleEntity -> {
      return new Role(roleEntity);
    }).collect(Collectors.toList());
  }
}
