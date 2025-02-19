package com.sfjs.gql.resolvers;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.sfjs.data.api.PaymentData;
import com.sfjs.data.core.NumericMetric;
import com.sfjs.gql.schema.BusinessDonation;
import com.sfjs.gql.schema.FellowDonation;
import com.sfjs.gql.svc.CheckoutService;
import com.sfjs.jpa.repo.NumericMetricRepository;

import reactor.core.publisher.Mono;

@RestController
@EnableWebMvc
@Transactional
public class Donation {

  Logger logger = Logger.getLogger(getClass().getName());

  @Autowired
  private CheckoutService checkoutService;

  @Autowired
  private NumericMetricRepository numericMetricRepository;

  @MutationMapping(name = "acceptBusinessDonation")
  public Mono<PaymentData> acceptBusinessDonation(@Argument(name = "donation") BusinessDonation donation) {
    logger.info("Donation: " + donation);
    return checkoutService.acceptBusinessDonation(donation);
  }

  @MutationMapping(name = "acceptFellowDonation")
  public Mono<PaymentData> acceptFellowDonation(@Argument(name = "donation") FellowDonation donation) {
    logger.info("Donation: " + donation);
    return checkoutService.acceptFellowDonation(donation);
  }

  @QueryMapping(name = "currentDonations")
  public String currentDonations() {
    NumericMetric fellowDonations = numericMetricRepository.findByName("CURRENT_FELLOW_DONATION");
    NumericMetric businessDonations = numericMetricRepository.findByName("CURRENT_BUSINESS_DONATION");
    return fellowDonations.getMetric().add(businessDonations.getMetric()).toString();
  }
}
