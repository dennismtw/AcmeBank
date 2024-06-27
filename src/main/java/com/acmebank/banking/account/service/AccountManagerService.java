package com.acmebank.banking.account.service;

import com.acmebank.banking.account.dto.AccountDto;
import com.acmebank.banking.account.dto.TransactionDetailDto;
import com.acmebank.banking.account.dto.TransferRequestDto;

public interface AccountManagerService {

    TransactionDetailDto transfer (TransferRequestDto transferRequestDto);

    AccountDto getAccBalByCustId (Long customerId);

    AccountDto getAccBalByAccNo(Long accNo);
}
