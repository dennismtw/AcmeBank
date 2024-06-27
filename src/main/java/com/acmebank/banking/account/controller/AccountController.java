package com.acmebank.banking.account.controller;

import com.acmebank.banking.account.dto.AccountDto;
import com.acmebank.banking.account.dto.ErrorResponseDto;
import com.acmebank.banking.account.dto.TransactionDetailDto;
import com.acmebank.banking.account.dto.TransferRequestDto;
import com.acmebank.banking.account.service.AccountManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "REST APIs for AcmeBank Account Service",
        description = "REST APIs in AcmeBank to retrieve account balance details and money transfer"
)
@RestController
@RequestMapping(path="/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class AccountController {

    private AccountManagerService accountManagerService;

    @Operation(
            summary = "Get Account Balance Detail by Customer ID REST API",
            description = "REST API to fetch Account details based on a Customer ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid Customer ID supplied."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Account with customer id provided is not exist in system.",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )

    // Assume Customer ID is 8 Digit value, add a validation on it
    @GetMapping("/getAccBalByCustId")
    public AccountDto getAccBalByCustId (@NotNull(message = "Customer ID can not be a null or empty") @Digits(integer = 8, fraction = 0, message = "Invalid Customer ID, please input ID within 1-8 digit.")
                                         @RequestParam(required = false) Long customerId) {
        return accountManagerService.getAccBalByCustId(customerId);
    }

    @Operation(
            summary = "Money Transfer REST API",
            description = "REST API to transfer money from sender account to receiver account"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid Sender or Receiver Account No supplied." +
                            " || Sender or Receiver Account No is not found in system." +
                            " || Sender account does not have enough balance."
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/transfer")
    public TransactionDetailDto transfer(@Valid @RequestBody TransferRequestDto transferRequestDto
    ) {
        return accountManagerService.transfer(transferRequestDto);
    }


    @Operation(
            summary = "Get Account Balance Detail by account no REST API",
            description = "REST API to fetch Account details based on a account no"
    )

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid Account No supplied."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Account No provided is not exist in system.",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )

    @GetMapping("/accounts/{accNo}")
    public AccountDto getAccBalByAccNo (@Digits(integer = 8, fraction = 0, message = "Invalid Acc No, please input ID within 1-8 digit.")
                                        @PathVariable Long accNo
    ) {
        return accountManagerService.getAccBalByAccNo(accNo);
    }


}
