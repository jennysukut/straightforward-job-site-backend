package com.sfjs.entity;

import com.sfjs.dto.BaseBody;
import com.sfjs.dto.Payment;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "payment")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PaymentEntity extends BaseEntity<PaymentEntity, Payment> {

  @Getter
  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id", nullable = false)
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
    }
  }
}