package com.sfjs.entity;

import com.sfjs.dto.BaseBody;
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
public class PaymentEntity extends BaseEntity<PaymentEntity, Payment> {

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
  @Transient
  private String checkoutToken; // from helcim - don't store

  @Getter
  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id", nullable = true)
  private AccountEntity account;

  @Override
  public <B extends BaseBody<?, ?>> void refresh(B body) {
    super.refresh(body);
    if (body instanceof Payment) {
      Payment paymentBody = (Payment) body;
      if (paymentBody.getAccount() != null) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.refresh(paymentBody.getAccount());
        this.setAccount(accountEntity);
      }
      if (paymentBody.getPaymentType() != null) {
        this.setPaymentType(paymentBody.getPaymentType());
      }
      if (paymentBody.getAmount() != null) {
        this.setAmount(paymentBody.getAmount());
      }
      if (paymentBody.getCurrency() != null) {
        this.setCurrency(paymentBody.getCurrency());
      }
      if (paymentBody.getCheckoutToken() != null) {
        this.setCheckoutToken(paymentBody.getCheckoutToken());
      }
    }
  }
}