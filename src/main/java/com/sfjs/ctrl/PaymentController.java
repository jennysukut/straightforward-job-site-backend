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

import com.sfjs.dto.Payment;
import com.sfjs.entity.PaymentEntity;
import com.sfjs.svc.PaymentService;

@RestController
@EnableWebMvc
@Transactional
public class PaymentController extends BaseController<PaymentService, PaymentEntity, Payment> {

  @Override
  protected Class<Payment> getBodyClass() {
    return Payment.class;
  }

  @Override
  protected Class<PaymentEntity> getEntityClass() {
    return PaymentEntity.class;
  }

  @RequestMapping(path = "/payment/{id}", method = RequestMethod.DELETE)
  public void delete(@PathVariable(name = "id") Long id) {
    super.delete(id);
  }

  @MutationMapping(name = "savePayment")
  public Payment savePayment(@Argument(name = "requestBody") Payment requestBody) {
    return save(requestBody);
  }

  @RequestMapping(path = "/payment", method = RequestMethod.POST)
  public Payment save(@RequestBody Payment requestBody) {
    return super.save(requestBody);
  }

  @RequestMapping(path = "/payment/getbyid/{id}", method = RequestMethod.GET)
  public Payment getById(@PathVariable("id") Long id) {
    return super.getById(id);
  }

  @RequestMapping(path = "/payment/findbyid/{id}", method = RequestMethod.GET)
  public Payment findById(@PathVariable("id") Long id) {
    return super.findById(id);
  }

  @RequestMapping(path = "/payment/findallbyid/{id}", method = RequestMethod.GET)
  public List<Payment> findAllById(@PathVariable("id") Long id) {
    return super.findAllById(id);
  }

  @RequestMapping(path = "/payment/findbyname/{name}", method = RequestMethod.GET)
  public Payment findByName(@PathVariable("name") String name) {
    return super.findByName(name);
  }

  @RequestMapping(path = "/payment/findallbyname/{name}", method = RequestMethod.GET)
  public List<Payment> findAllByName(@PathVariable("name") String name) {
    return super.findAllByName(name);
  }

  @RequestMapping(path = "/payment/findbylabel/{label}", method = RequestMethod.GET)
  public Payment findByLabel(@PathVariable("label") String label) {
    return super.findByLabel(label);
  }

  @RequestMapping(path = "/payment/findallbylabel/{label}", method = RequestMethod.GET)
  public List<Payment> findAllByLabel(@PathVariable("label") String label) {
    return super.findAllByLabel(label);
  }

  @QueryMapping(name = "findAllPayments")
  @RequestMapping(path = "/payment/findall", method = RequestMethod.GET)
  public List<Payment> findAll(@Argument Integer limit) {
    return super.findAll(limit);
  }
}
