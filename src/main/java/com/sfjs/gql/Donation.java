package com.sfjs.gql;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.sfjs.dto.BusinessDonation;
import com.sfjs.dto.FellowDonation;
import com.sfjs.dto.Payment;
import com.sfjs.svc.DonationService;

import reactor.core.publisher.Mono;

@RestController
@EnableWebMvc
@Transactional
public class Donation {

  Logger logger = Logger.getLogger(getClass().getName());

  @Autowired
  DonationService service;

  @MutationMapping(name = "acceptBusinessDonation")
  public Mono<Payment> acceptBusinessDonation(@Argument(name = "donation") BusinessDonation donation) {
    logger.info("Donation: " + donation);
    return service.acceptBusinessDonation(donation);
  }

  @MutationMapping(name = "acceptFellowDonation")
  public Mono<Payment> acceptFellowDonation(@Argument(name = "donation") FellowDonation donation) {
    logger.info("Donation: " + donation);
    return service.acceptFellowDonation(donation);
  }

  @QueryMapping(name = "currentDonations")
  public String currentDonations() {
    return service.getCurrentDonations();
  }
}
