package com.sfjs.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.dto.BusinessDonation;
import com.sfjs.dto.Payment;

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
  PaymentService paymentService;

  public Mono<Payment> acceptBusinessDonation(BusinessDonation donation) {
    return checkoutService.acceptBusinessDonation(donation);
  }

}
