package com.sfjs.gql.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.crud.response.NumericMetricResponse;
import com.sfjs.crud.response.PaymentResponse;
import com.sfjs.crud.svc.BusinessService;
import com.sfjs.crud.svc.NumericMetricService;
import com.sfjs.dto.BusinessDonation;
import com.sfjs.dto.FellowDonation;

import jakarta.transaction.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class DonationService {

  @Autowired
  BusinessService businessService;

  @Autowired
  CheckoutService checkoutService;

  @Autowired
  NumericMetricService numericMetricService;

  public Mono<PaymentResponse> acceptBusinessDonation(BusinessDonation donation) {
    return checkoutService.acceptBusinessDonation(donation);
  }

  public Mono<PaymentResponse> acceptFellowDonation(FellowDonation donation) {
    return checkoutService.acceptFellowDonation(donation);
  }

  public String getCurrentDonations() {
    NumericMetricResponse fellowDonations = numericMetricService.findByName("CURRENT_FELLOW_DONATION");
    NumericMetricResponse businessDonations = numericMetricService.findByName("CURRENT_BUSINESS_DONATION");
    return fellowDonations.getMetric().add(businessDonations.getMetric()).toString();
  }
}
