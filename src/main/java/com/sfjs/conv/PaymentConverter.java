package com.sfjs.conv;

import org.springframework.stereotype.Service;

import com.sfjs.dto.Account;
import com.sfjs.dto.Payment;
import com.sfjs.entity.AccountEntity;
import com.sfjs.entity.PaymentEntity;

@Service
public class PaymentConverter extends BaseConverter<PaymentEntity, Payment> {

  AccountConverter accountConverter;

  public PaymentConverter(AccountConverter accountConverter) {
    super(PaymentEntity.class, Payment.class);
    this.accountConverter = accountConverter;
  }

  @Override
  public PaymentEntity convertToEntity(Payment payment) {
    Account account = new Account();
    account.setName(payment.getAccountName());
    account.setEmail(payment.getEmail());
    account.setEnabled(true);
    // A payment does not have a password
//    account.setPassword(business.getPassword());
    // TODO consider getting rid of role entities
//    Role role = new Role();
//    role.setName("BUSINESS");
//    account.setRoles(Set.of(role));

    // This is where the password would get encrypted if there was one
    AccountEntity accountEntity = accountConverter.convertToEntity(account);

    PaymentEntity entity = super.convertToEntity(payment);
    entity.setAccount(accountEntity);
    entity.setAmount(payment.getAmount());
    entity.setCheckoutToken(payment.getCheckoutToken());
    entity.setCurrency(payment.getCurrency());
    entity.setPaymentType(payment.getPaymentType());
    entity.setSALT(payment.getSALT());
    entity.setSecretToken(payment.getSecretToken());

    return entity;
  }

  @Override
  public Payment convertToBody(PaymentEntity entity) {
    Payment payment = new Payment();
    payment.setAccountName(entity.getAccount().getName());
    payment.setAmount(entity.getAmount());
    payment.setCheckoutToken(entity.getCheckoutToken());
    payment.setCurrency(entity.getCurrency());
    payment.setEmail(entity.getAccount().getEmail());
    payment.setPaymentType(entity.getPaymentType());
    return payment;
  }

}
