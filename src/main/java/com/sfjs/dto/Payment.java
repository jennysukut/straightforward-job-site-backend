package com.sfjs.dto;

import com.sfjs.entity.BaseEntity;
import com.sfjs.entity.PaymentEntity;

import lombok.Getter;
import lombok.Setter;

public class Payment extends BaseBody<Payment, PaymentEntity> {

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
    }
  }
}
