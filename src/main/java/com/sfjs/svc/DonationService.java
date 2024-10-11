package com.sfjs.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.dto.BusinessDonation;
import com.sfjs.dto.FellowDonation;
import com.sfjs.dto.response.NumericMetricResponse;
import com.sfjs.dto.response.PaymentResponse;

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
