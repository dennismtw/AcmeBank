package com.acmebank.banking.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(
        name = "Transfer Request DTO",
        description = "Schema to hold Transfer Request information"
)
public class TransferRequestDto {

    @NotNull(message = "Sender Account Number can not be a null or empty")
    @Digits(integer = 8, fraction = 0, message = "Invalid Sender Acc No. , please input ID within 1-8 digit.")
    @Schema(
            description = "Sender Account No.", example = "12345678"
    )
    Long fromAccNo;

    @NotNull(message = "Receiver Account Number can not be a null or empty")
    @Digits(integer = 8, fraction = 0, message = "Invalid Receiver Acc No. , please input ID within 1-8 digit.")
    @Schema(
            description = "Receiver Account No.", example = "88888888"
    )
    Long toAccNo;

    @NotNull(message = "Transfer Amount can not be a null or empty")
    @DecimalMin(value = "0.000", inclusive = false, message = "Invalid transfer amount. , please input a positive transfer amount")
    @Digits(integer=10, fraction=3, message = "Invalid transfer amount. , please input a valid transfer amount")
    @Schema(
            description = "Transfer Amount", example = "100.000"
    )
    BigDecimal amount;
}
