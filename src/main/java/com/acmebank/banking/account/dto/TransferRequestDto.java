package com.acmebank.banking.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(
        name = "Transfer Request DTO",
        description = "Schema to hold Transfer Request information"
)
public class TransferRequestDto {


    @Digits(integer = 8, fraction = 0, message = "Invalid Sender Acc No. , please input ID within 1-8 digit.")
    @NotEmpty(message = "Sender Account Number can not be a null or empty")
    @Schema(
            description = "Sender Account No.", example = "12345678"
    )
    Long fromAccNo;

    @Digits(integer = 8, fraction = 0, message = "Invalid Receiver Acc No. , please input ID within 1-8 digit.")
    @NotEmpty(message = "Receiver Account Number can not be a null or empty")
    @Schema(
            description = "Receiver Account No.", example = "88888888"
    )
    Long toAccNo;

    @NotEmpty(message = "Transfer Amount can not be a null or empty")
    @Schema(
            description = "Transfer Amount", example = "100.000"
    )
    BigDecimal amount;
}
