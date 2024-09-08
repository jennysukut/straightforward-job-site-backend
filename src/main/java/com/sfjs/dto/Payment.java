package com.sfjs.dto;

import com.sfjs.entity.BaseEntity;
import com.sfjs.entity.PaymentEntity;

import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

public class Payment extends BaseBody<Payment, PaymentEntity> {

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
  private Account account;

  @Override
  public <E extends BaseEntity<?, ?>> void refresh(E entity) {
    super.refresh(entity);
    if (entity instanceof PaymentEntity) {
      PaymentEntity paymentEntity = (PaymentEntity) entity;
      if (paymentEntity.getAccount() != null) {
        Account account = new Account();
        account.refresh(paymentEntity.getAccount());
        this.setAccount(account);
      }
      if (paymentEntity.getPaymentType() != null) {
        this.setPaymentType(paymentEntity.getPaymentType());
      }
      if (paymentEntity.getAmount() != null) {
        this.setAmount(paymentEntity.getAmount());
      }
      if (paymentEntity.getCurrency() != null) {
        this.setCurrency(paymentEntity.getCurrency());
      }
      if (paymentEntity.getCheckoutToken() != null) {
        this.setCheckoutToken(paymentEntity.getCheckoutToken());
      }
    }
  }
}
