package com.acmebank.banking.account.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class TransactionDetail extends BaseEntity{
    @Id
    private Long id;
}
