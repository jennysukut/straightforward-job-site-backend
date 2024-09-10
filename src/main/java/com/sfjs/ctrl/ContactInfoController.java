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

import com.sfjs.dto.ContactInfo;
import com.sfjs.entity.ContactInfoEntity;
import com.sfjs.svc.ContactInfoService;

@RestController
@EnableWebMvc
@Transactional
public class ContactInfoController extends BaseController<ContactInfoService, ContactInfoEntity, ContactInfo> {

  @MutationMapping(name = "deleteContactInfo")
  public Boolean deleteContactInfo(@Argument(name = "id") Long id) {
    return delete(id);
  }

  @RequestMapping(path = "/contactInfo/{id}", method = RequestMethod.DELETE)
  public Boolean delete(@PathVariable(name = "id") Long id) {
    return super.delete(id);
  }

  @MutationMapping(name = "saveContactInfo")
  public ContactInfo saveContactInfo(@Argument(name = "requestBody") ContactInfo requestBody) {
    return save(requestBody);
  }

  @RequestMapping(path = "/contactInfo", method = RequestMethod.POST)
  public ContactInfo save(@RequestBody ContactInfo requestBody) {
    return super.save(requestBody);
  }

  @RequestMapping(path = "/contactInfo/getbyid/{id}", method = RequestMethod.GET)
  public ContactInfo getById(@PathVariable("id") Long id) {
    return super.getById(id);
  }

  @RequestMapping(path = "/contactInfo/findbyid/{id}", method = RequestMethod.GET)
  public ContactInfo findById(@PathVariable("id") Long id) {
    return super.findById(id);
  }

  @RequestMapping(path = "/contactInfo/findallbyid/{id}", method = RequestMethod.GET)
  public List<ContactInfo> findAllById(@PathVariable("id") Long id) {
    return super.findAllById(id);
  }

  @RequestMapping(path = "/contactInfo/findbyname/{name}", method = RequestMethod.GET)
  public ContactInfo findByName(@PathVariable("name") String name) {
    return super.findByName(name);
  }

  @RequestMapping(path = "/contactInfo/findallbyname/{name}", method = RequestMethod.GET)
  public List<ContactInfo> findAllByName(@PathVariable("name") String name) {
    return super.findAllByName(name);
  }

  @RequestMapping(path = "/contactInfo/findbylabel/{label}", method = RequestMethod.GET)
  public ContactInfo findByLabel(@PathVariable("label") String label) {
    return super.findByLabel(label);
  }

  @RequestMapping(path = "/contactInfo/findallbylabel/{label}", method = RequestMethod.GET)
  public List<ContactInfo> findAllByLabel(@PathVariable("label") String label) {
    return super.findAllByLabel(label);
  }

  @QueryMapping(name = "findAllContactInfo")
  @RequestMapping(path = "/contactInfo/findall", method = RequestMethod.GET)
  public List<ContactInfo> findAll(@Argument Integer limit) {
    return super.findAll(limit);
  }
}
