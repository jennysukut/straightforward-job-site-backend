package com.sfjs.gql.resolvers;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.sfjs.crud.response.PaymentResponse;
import com.sfjs.gql.schema.BusinessDonation;
import com.sfjs.gql.schema.FellowDonation;
import com.sfjs.gql.svc.DonationService;

import reactor.core.publisher.Mono;

@RestController
@EnableWebMvc
@Transactional
public class Donation {

  Logger logger = Logger.getLogger(getClass().getName());

  @Autowired
  DonationService service;

  @MutationMapping(name = "acceptBusinessDonation")
  public Mono<PaymentResponse> acceptBusinessDonation(@Argument(name = "donation") BusinessDonation donation) {
    logger.info("Donation: " + donation);
    return service.acceptBusinessDonation(donation);
  }

  @MutationMapping(name = "acceptFellowDonation")
  public Mono<PaymentResponse> acceptFellowDonation(@Argument(name = "donation") FellowDonation donation) {
    logger.info("Donation: " + donation);
    return service.acceptFellowDonation(donation);
  }

  @QueryMapping(name = "currentDonations")
  public String currentDonations() {
    return service.getCurrentDonations();
  }
}
