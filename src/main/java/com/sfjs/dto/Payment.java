package com.sfjs.dto;

import com.sfjs.entity.PaymentEntity;

import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

public class Payment extends BaseBody {

  @Getter
  @Setter
  private String paymentType; // example: "purchase"

  @Getter
  @Setter
  private String amount; // example: "0.01"

  @Getter
  @Setter
  private String currency; // example: "CAD"

  @Getter
  @Setter
  @Transient
  private String checkoutToken; // from helcim - don't store

  @Getter
  @Setter
  private String accountName;

  @Getter
  @Setter
  private String email;

  public Payment() {
  }

  public Payment(PaymentEntity entity) {
    super(entity);
    this.accountName = entity.getAccount().getName();
    this.email = entity.getAccount().getEmail();
    this.amount = entity.getAmount();
    this.checkoutToken = entity.getCheckoutToken();
    this.currency = entity.getCurrency();
    this.paymentType = entity.getPaymentType();
  }
}
