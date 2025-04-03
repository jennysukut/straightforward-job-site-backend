package com.sfjs.gql.resolvers;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import com.sfjs.data.core.ClientCheckoutData;
import com.sfjs.gql.svc.CheckoutService;

import reactor.core.publisher.Mono;

@RestController
@Transactional
public class Donation {

  Logger logger = Logger.getLogger(getClass().getName());

  @Autowired
  private CheckoutService checkoutService;

//  @Autowired
//  private NumericMetricService numericMetricService;

  @MutationMapping(name = "acceptBusinessDonation")
  public Mono<ClientCheckoutData> acceptBusinessDonation(
      @Argument(name = "email") String email,
      @Argument(name = "password") String password,
      @Argument(name = "name") String name,
      @Argument(name = "isBetaTester") Optional<Boolean> isBetaTester,
      @Argument(name = "contactName") String contactName,
      @Argument(name = "isEarlySignup") Optional<Boolean> isEarlySignup,
      @Argument(name = "referral") String referral,
      @Argument(name = "amount") String amount,
      @Argument(name = "currency") String currency,
      @Argument(name = "paymentType") String paymentType) {
    logger.info(String.format("acceptBusinessDonation: email [%s] password [%s]", email, password));
    return checkoutService.acceptBusinessDonation(email, password, name, isBetaTester, contactName, isEarlySignup, referral, amount, currency, paymentType);
  }

  @MutationMapping(name = "acceptFellowDonation")
  public Mono<ClientCheckoutData> acceptFellowDonation(
      @Argument(name="email") String email,
      @Argument(name="password") String password,
      @Argument(name="name") String name,
      @Argument(name="isBetaTester") Optional<Boolean> isBetaTester,
      @Argument(name="isCollaborator") Optional<Boolean> isCollaborator,
      @Argument(name="message") String message,
      @Argument(name="referralCode") String referralCode,
      @Argument(name="isReferralPartner") Optional<Boolean> isReferralPartner,
      @Argument(name = "amount")String amount,
      @Argument(name = "currency")String currency,
      @Argument(name = "paymentType")String paymentType) {
    logger.info(String.format("acceptFellowDonation: email [%s] password [%s]", email, password));
    return checkoutService.acceptFellowDonation(email, password, name, isBetaTester, isCollaborator, message,
        referralCode, isReferralPartner, amount, currency, paymentType);
  }

//  @QueryMapping(name = "currentDonations")
//  public String currentDonations() {
//    NumericMetric fellowDonations = numericMetricService.findByName("CURRENT_FELLOW_DONATION");
//    NumericMetric businessDonations = numericMetricService.findByName("CURRENT_BUSINESS_DONATION");
//    return fellowDonations.getMetric().add(businessDonations.getMetric()).toString();
//  }
}
