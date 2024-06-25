package com.acmebank.banking.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(
        name = "Account DTO",
        description = "Schema to hold Account information"
)
public class AccountDto {

    @NotEmpty(message = "AccountNumber can not be a null or empty")
    @Pattern(regexp="(^$|[0-9]{8})",message = "AccountNumber must be 8 digits")
    @Schema(
            description = "Account Number of Acme Bank account", example = "12345678"
    )
    private Long accNo;
    private Long customerId;

    @NotEmpty(message = "AccountType can not be a null or empty")
    @Schema(
            description = "Account type of Acme Bank account", example = "Savings/Current"
    )
    private String accType;

    private String currencyCode;
    private BigDecimal accBalance;

}
