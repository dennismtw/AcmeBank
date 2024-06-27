package com.acmebank.banking.account.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Account extends BaseEntity {

    @Id
    @Column(name = "acc_no")
    private Long accNo;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "acc_type")
    private String accType;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "acc_balance")
    private BigDecimal accBalance;
}