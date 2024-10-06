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

import com.sfjs.dto.NumericMetric;
import com.sfjs.entity.NumericMetricEntity;
import com.sfjs.svc.NumericMetricService;

@RestController
@EnableWebMvc
@Transactional
public class NumericMetricController extends BaseController<NumericMetricService, NumericMetricEntity, NumericMetric> {

  @MutationMapping(name = "deleteNumericMetric")
  public Boolean deleteNumericMetric(@Argument(name = "id") Long id) {
    return delete(id);
  }

  @RequestMapping(path = "/numericMetric/{id}", method = RequestMethod.DELETE)
  public Boolean delete(@PathVariable(name = "id") Long id) {
    return super.delete(id);
  }

  @MutationMapping(name = "saveNumericMetric")
  public NumericMetric saveNumericMetric(@Argument(name = "requestBody") NumericMetric requestBody) {
    return save(requestBody);
  }

  @RequestMapping(path = "/numericMetric", method = RequestMethod.POST)
  public NumericMetric save(@RequestBody NumericMetric requestBody) {
    return super.save(requestBody);
  }

  @RequestMapping(path = "/numericMetric/getbyid/{id}", method = RequestMethod.GET)
  public NumericMetric getById(@PathVariable("id") Long id) {
    return super.getById(id);
  }

  @RequestMapping(path = "/numericMetric/findbyid/{id}", method = RequestMethod.GET)
  public NumericMetric findById(@PathVariable("id") Long id) {
    return super.findById(id);
  }

  @RequestMapping(path = "/numericMetric/findallbyid/{id}", method = RequestMethod.GET)
  public List<NumericMetric> findAllById(@PathVariable("id") Long id) {
    return super.findAllById(id);
  }

  @RequestMapping(path = "/numericMetric/findbyname/{name}", method = RequestMethod.GET)
  public NumericMetric findByName(@PathVariable("name") String name) {
    return super.findByName(name);
  }

  @RequestMapping(path = "/numericMetric/findallbyname/{name}", method = RequestMethod.GET)
  public List<NumericMetric> findAllByName(@PathVariable("name") String name) {
    return super.findAllByName(name);
  }

  @RequestMapping(path = "/numericMetric/findbylabel/{label}", method = RequestMethod.GET)
  public NumericMetric findByLabel(@PathVariable("label") String label) {
    return super.findByLabel(label);
  }

  @RequestMapping(path = "/numericMetric/findallbylabel/{label}", method = RequestMethod.GET)
  public List<NumericMetric> findAllByLabel(@PathVariable("label") String label) {
    return super.findAllByLabel(label);
  }

  @QueryMapping(name = "findAllNumericMetrices")
  @RequestMapping(path = "/numericMetric/findall", method = RequestMethod.GET)
  public List<NumericMetric> findAll(@Argument(name = "limit") Integer limit) {
    return super.findAll(limit);
  }
}
