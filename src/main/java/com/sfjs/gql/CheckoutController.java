package com.sfjs.gql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.sfjs.dto.Payment;
import com.sfjs.svc.CheckoutService;

import reactor.core.publisher.Mono;

@RestController
@EnableWebMvc
@Transactional
public class CheckoutController {

  @Autowired
  CheckoutService service;

  @MutationMapping(name = "initializePayment")
  public Mono<Payment> initializePayment(@Argument(name = "payment") Payment payment) {
    return service.initializePayment(payment);
  }
}
