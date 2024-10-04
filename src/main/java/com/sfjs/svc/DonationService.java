package com.sfjs.svc;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.dto.BusinessDonation;
import com.sfjs.dto.FellowDonation;
import com.sfjs.dto.Payment;
import com.sfjs.entity.PaymentStatus;

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

  public Mono<Payment> acceptFellowDonation(FellowDonation donation) {
    return checkoutService.acceptFellowDonation(donation);
  }

  public String getCurrentDonations() {
    BigDecimal currentDonations = BigDecimal.ZERO;
    for (Payment payment: paymentService.findAll()) {
      if (payment.getStatus().contentEquals(PaymentStatus.APPROVED.name())) {
        currentDonations = currentDonations.add(new BigDecimal(payment.getAmount()));
      }
    }
    return currentDonations.toString();
  }
}
