package com.acmebank.banking.account.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class TransactionDetail extends BaseEntity{

    @Id
    @Column(name = "txn_no")
    private String txnNo;

    @Column(name = "sender_acc_no")
    private Long senderAccNo;

    @Column(name = "receiver_acc_no")
    private Long receiverAccNo;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "transfer_dt")
    private LocalDateTime transferDt;
}
