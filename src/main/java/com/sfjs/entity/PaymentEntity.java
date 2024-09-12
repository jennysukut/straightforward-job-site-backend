package com.sfjs.entity;

import com.sfjs.dto.Payment;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "payment")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PaymentEntity extends BaseEntity {

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
  private String secretToken; // from helcim - store encrypted

  @Getter
  @Setter
  private String SALT; // For encryption of secret token

  @Getter
  @Setter
  @Transient
  private String checkoutToken; // from helcim - don't store

  @Getter
  @Setter
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "account_id", nullable = true)
  private AccountEntity account;

  public PaymentEntity() {
  }

  public PaymentEntity(Payment payment) {
    this.amount = payment.getAmount();
    this.checkoutToken = payment.getCheckoutToken();
    this.currency = payment.getCurrency();
    this.paymentType = payment.getPaymentType();
    this.account = new AccountEntity();
    this.account.setName(payment.getAccountName());
    this.account.setEmail(payment.getEmail());
 }
}