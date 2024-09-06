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
import com.sfjs.dto.Address;
import com.sfjs.entity.AddressEntity;
import com.sfjs.svc.AddressService;

@RestController
@EnableWebMvc
@Transactional
public class AddressController {

  @Autowired
  AddressService service;

  Logger logger = Logger.getLogger(AddressController.class.getName());
  ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

  @RequestMapping(path = "/address/{id}", method = RequestMethod.DELETE)
  public void delete(@PathVariable(name = "id") Long id) {
    service.delete(id);
  }

  @MutationMapping(name = "saveAddress")
  public Address saveAddress(@Argument(name = "requestBody") Address requestBody) {
    return save(requestBody);
  }

  @RequestMapping(path = "/address", method = RequestMethod.POST)
  public Address save(@RequestBody Address requestBody) {
    AddressEntity addressEntity;
    if (requestBody.getId() == null) {
      addressEntity = new AddressEntity();
    } else {
      addressEntity = service.getById(requestBody.getId());
    }
    if (requestBody.getName() != null) {
      addressEntity.setName(requestBody.getName());
    }
    if (requestBody.getLabel() != null) {
      addressEntity.setLabel(requestBody.getLabel());
    }
    if (requestBody.getStreetAddress() != null) {
      addressEntity.setStreetAddress(requestBody.getStreetAddress());
    }
    if (requestBody.getSecondLine() != null) {
      addressEntity.setSecondLine(requestBody.getSecondLine());
    }
    if (requestBody.getCity() != null) {
      addressEntity.setCity(requestBody.getCity());
    }
    if (requestBody.getState() != null) {
      addressEntity.setState(requestBody.getState());
    }
    if (requestBody.getCountry() != null) {
      addressEntity.setCountry(requestBody.getCountry());
    }
    if (requestBody.getZipCode() != null) {
      addressEntity.setZipCode(requestBody.getZipCode());
    }
    if (requestBody.getLandmark() != null) {
      addressEntity.setLandmark(requestBody.getLandmark());
    }
    return new Address(service.save(addressEntity));
  }

  @RequestMapping(path = "/address/getbyid/{id}", method = RequestMethod.GET)
  public Address getById(@PathVariable("id") Long id) {
    logger.info("Path variable: " + id);
    AddressEntity addressEntity = service.getById(id);
    Address address = new Address(addressEntity);
    return address;
  }

  @RequestMapping(path = "/address/findbyid/{id}", method = RequestMethod.GET)
  public Address findById(@PathVariable("id") Long id) {
    logger.info("Path variable: " + id);
    return new Address(service.findById(id));
  }

  @RequestMapping(path = "/address/findallbyid/{id}", method = RequestMethod.GET)
  public List<Address> findAllById(@PathVariable("id") Long id) {
    logger.info("Path variable: " + id);
    return service.findAllById(id).stream().map(addressEntity -> {
      return new Address(addressEntity);
    }).collect(Collectors.toList());
  }

  @RequestMapping(path = "/address/findbyname/{name}", method = RequestMethod.GET)
  public Address findByName(@PathVariable("name") String name) {
    logger.info("Path variable: " + name);
    return new Address(service.findByName(name));
  }

  @RequestMapping(path = "/address/findallbyname/{name}", method = RequestMethod.GET)
  public List<Address> findAllByName(@PathVariable("name") String name) {
    logger.info("Path variable: " + name);
    return service.findAllByName(name).stream().map(addressEntity -> {
      return new Address(addressEntity);
    }).collect(Collectors.toList());
  }

  @RequestMapping(path = "/address/findbylabel/{label}", method = RequestMethod.GET)
  public Address findByLabel(@PathVariable("label") String label) {
    logger.info("Path variable: " + label);
    return new Address(service.findByLabel(label));
  }

  @RequestMapping(path = "/address/findallbylabel/{label}", method = RequestMethod.GET)
  public List<Address> findAllByLabel(@PathVariable("label") String label) {
    logger.info("Path variable: " + label);
    return service.findAllByLabel(label).stream().map(addressEntity -> {
      return new Address(addressEntity);
    }).collect(Collectors.toList());
  }

  @QueryMapping(name = "findAllAddresses")
  @RequestMapping(path = "/address/findall", method = RequestMethod.GET)
  public List<Address> findAll(@Argument Integer limit) {
    logger.info("Request param limit: " + limit);
    Stream<AddressEntity> addressStream = service.findAll().stream();
    if (limit != null) {
      addressStream = addressStream.limit(limit);
    }
    return addressStream.map(addressEntity -> {
      return new Address(addressEntity);
    }).collect(Collectors.toList());
  }
}
