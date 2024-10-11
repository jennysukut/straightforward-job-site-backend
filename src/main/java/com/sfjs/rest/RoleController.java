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

import com.sfjs.dto.Role;
import com.sfjs.dto.response.RoleResponse;
import com.sfjs.entity.RoleEntity;
import com.sfjs.svc.RoleService;

@RestController
@EnableWebMvc
@Transactional
public class RoleController extends BaseController<RoleService, RoleEntity, Role, RoleResponse> {

  @MutationMapping(name = "deleteRole")
  public Boolean deleteRole(@Argument(name = "id") Long id) {
    return delete(id);
  }

  @RequestMapping(path = "/role/{id}", method = RequestMethod.DELETE)
  public Boolean delete(@PathVariable(name = "id") Long id) {
    return super.delete(id);
  }

  @MutationMapping(name = "saveRole")
  public RoleResponse saveRole(@Argument(name = "requestBody") Role requestBody) {
    return save(requestBody);
  }

  @RequestMapping(path = "/role", method = RequestMethod.POST)
  public RoleResponse save(@RequestBody Role requestBody) {
    return super.save(requestBody);
  }

  @RequestMapping(path = "/role/getbyid/{id}", method = RequestMethod.GET)
  public RoleResponse getById(@PathVariable("id") Long id) {
    return super.getById(id);
  }

  @RequestMapping(path = "/role/findbyid/{id}", method = RequestMethod.GET)
  public RoleResponse findById(@PathVariable("id") Long id) {
    return super.findById(id);
  }

  @RequestMapping(path = "/role/findallbyid/{id}", method = RequestMethod.GET)
  public List<RoleResponse> findAllById(@PathVariable("id") Long id) {
    return super.findAllById(id);
  }

  @RequestMapping(path = "/role/findbyname/{name}", method = RequestMethod.GET)
  public RoleResponse findByName(@PathVariable("name") String name) {
    return super.findByName(name);
  }

  @RequestMapping(path = "/role/findallbyname/{name}", method = RequestMethod.GET)
  public List<RoleResponse> findAllByName(@PathVariable("name") String name) {
    return super.findAllByName(name);
  }

  @RequestMapping(path = "/role/findbylabel/{label}", method = RequestMethod.GET)
  public RoleResponse findByLabel(@PathVariable("label") String label) {
    return super.findByLabel(label);
  }

  @RequestMapping(path = "/role/findallbylabel/{label}", method = RequestMethod.GET)
  public List<RoleResponse> findAllByLabel(@PathVariable("label") String label) {
    return super.findAllByLabel(label);
  }

  @QueryMapping(name = "findAllRoles")
  @RequestMapping(path = "/role/findall", method = RequestMethod.GET)
  public List<RoleResponse> findAll(@Argument(name = "limit") Integer limit) {
    return super.findAll(limit);
  }
}
