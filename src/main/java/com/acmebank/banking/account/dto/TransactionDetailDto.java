package com.acmebank.banking.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(
        name = "Transfer Detail DTO",
        description = "Schema to hold Transfer Detail information"
)
public class TransactionDetailDto {


    private String txnNo;

    private Long senderAccNo;

    private Long receiverAccNo;

    private String currencyCode;

    private BigDecimal amount;

    private LocalDateTime transferDt;

}
