package com.acmebank.banking.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(
        name = "Account DTO",
        description = "Schema to hold Account information"
)
public class AccountDto {

    @Schema(
            description = "Account Number of Acme Bank account", example = "12345678"
    )
    private Long accNo;
    @Schema(
            description = "Customer ID of Acme Bank account", example = "1"
    )
    private Long customerId;

    @Schema(
            description = "Account type of Acme Bank account", example = "Savings/Current"
    )
    private String accType;
    @Schema(
            description = "Account Currency Code", example = "HKD"
    )
    private String currencyCode;
    @Schema(
            description = "Account Balance", example = "1000000.000"
    )
    private BigDecimal accBalance;

}
