package com.sfjs.dto;

import lombok.Getter;
import lombok.Setter;

public class TransactionDataInput {
  @Getter
  @Setter
  private String transactionId;

  @Getter
  @Setter
  private String dateCreated;

  @Getter
  @Setter
  private String cardBatchId;

  @Getter
  @Setter
  private String status;

  @Getter
  @Setter
  private String type;

  @Getter
  @Setter
  private String amount;

  @Getter
  @Setter
  private String currency;

  @Getter
  @Setter
  private String avsResponse;

  @Getter
  @Setter
  private String cvvResponse;

  @Getter
  @Setter
  private String approvalCode;

  @Getter
  @Setter
  private String cardToken;

  @Getter
  @Setter
  private String cardNumber;

  @Getter
  @Setter
  private String cardHolderName;

  @Getter
  @Setter
  private String customerCode;

  @Getter
  @Setter
  private String invoiceNumber;

  @Getter
  @Setter
  private String warning;
}
