package com.acmebank.banking.account.controller;

import com.acmebank.banking.account.dto.AccountDto;
import com.acmebank.banking.account.dto.ErrorResponseDto;
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
        name = "CRUD REST APIs for Accounts in AcmeBank",
        description = "CRUD REST APIs in AcmeBank to CREATE, UPDATE, FETCH AND DELETE account details"
)
@RestController
@RequestMapping(path="/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class AccountController {

    private AccountManagerService accountManagerService;

    @Operation(
            summary = "Fetch Account Balance REST API",
            description = "REST API to fetch Customer &  Account details based on a custoemr id"
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
    public AccountDto getAccBalByCustId (@NotNull @Digits(integer = 8, fraction = 0, message = "Invalid Customer ID, please input ID within 1-8 digit.")
                                         @RequestParam Long customerId) {
        return accountManagerService.getAccBalByCustId(customerId);
    }


    @PostMapping("/transfer")
    public AccountDto transfer(@Valid @RequestBody TransferRequestDto transferRequestDto
    ) {
        return accountManagerService.transfer(transferRequestDto);
    }

    @GetMapping("/getAccBalByAccNo")
    public AccountDto getAccBalByAccNo (@NotNull @Digits(integer = 8, fraction = 0, message = "Invalid Acc No, please input ID within 1-8 digit.")
                                         @RequestParam Long accNo
    ) {

        return accountManagerService.getAccBalByAccNo(accNo);
    }


}
