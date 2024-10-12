package com.sfjs.gql.resolvers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sfjs.crud.svc.PaymentService;
import com.sfjs.gql.schema.PaymentResult;
import com.sfjs.gql.schema.PaymentResultInput;

@RestController
@EnableWebMvc
@Transactional
public class Payment {

  @Autowired
  PaymentService service;

  @MutationMapping(name = "completePayment")
  public PaymentResult completePayment(@Argument(name = "input") PaymentResultInput input)
      throws JsonProcessingException {
    return service.completePayment(input);
  }
}
