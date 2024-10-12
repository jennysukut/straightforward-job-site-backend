package com.sfjs.crud.ctrl;

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

import com.sfjs.crud.entity.PaymentEntity;
import com.sfjs.crud.request.PaymentRequest;
import com.sfjs.crud.response.PaymentResponse;
import com.sfjs.crud.svc.PaymentService;

@RestController
@EnableWebMvc
@Transactional
public class PaymentController extends BaseController<PaymentService, PaymentEntity, PaymentRequest, PaymentResponse> {

  @MutationMapping(name = "deletePayment")
  public Boolean deletePayment(@Argument(name = "id") Long id) {
    return delete(id);
  }

  @RequestMapping(path = "/payment/{id}", method = RequestMethod.DELETE)
  public Boolean delete(@PathVariable(name = "id") Long id) {
    return super.delete(id);
  }

  @MutationMapping(name = "savePayment")
  public PaymentResponse savePayment(@Argument(name = "requestBody") PaymentRequest requestBody) {
    return save(requestBody);
  }

  @RequestMapping(path = "/payment", method = RequestMethod.POST)
  public PaymentResponse save(@RequestBody PaymentRequest requestBody) {
    return super.save(requestBody);
  }

  @RequestMapping(path = "/payment/getbyid/{id}", method = RequestMethod.GET)
  public PaymentResponse getById(@PathVariable("id") Long id) {
    return super.getById(id);
  }

  @RequestMapping(path = "/payment/findbyid/{id}", method = RequestMethod.GET)
  public PaymentResponse findById(@PathVariable("id") Long id) {
    return super.findById(id);
  }

  @RequestMapping(path = "/payment/findallbyid/{id}", method = RequestMethod.GET)
  public List<PaymentResponse> findAllById(@PathVariable("id") Long id) {
    return super.findAllById(id);
  }

  @RequestMapping(path = "/payment/findbyname/{name}", method = RequestMethod.GET)
  public PaymentResponse findByName(@PathVariable("name") String name) {
    return super.findByName(name);
  }

  @RequestMapping(path = "/payment/findallbyname/{name}", method = RequestMethod.GET)
  public List<PaymentResponse> findAllByName(@PathVariable("name") String name) {
    return super.findAllByName(name);
  }

  @RequestMapping(path = "/payment/findbylabel/{label}", method = RequestMethod.GET)
  public PaymentResponse findByLabel(@PathVariable("label") String label) {
    return super.findByLabel(label);
  }

  @RequestMapping(path = "/payment/findallbylabel/{label}", method = RequestMethod.GET)
  public List<PaymentResponse> findAllByLabel(@PathVariable("label") String label) {
    return super.findAllByLabel(label);
  }

  @QueryMapping(name = "findAllPayments")
  @RequestMapping(path = "/payment/findall", method = RequestMethod.GET)
  public List<PaymentResponse> findAll(@Argument(name = "limit") Integer limit) {
    return super.findAll(limit);
  }
}
