package com.acmebank.banking.account.service;

import com.acmebank.banking.account.dto.AccountDto;
import com.acmebank.banking.account.dto.TransactionDetailDto;
import com.acmebank.banking.account.dto.TransferRequestDto;
import com.acmebank.banking.account.entity.TransactionDetail;

public interface AccountManagerService {

    public TransactionDetailDto transfer (TransferRequestDto transferRequestDto);

    public AccountDto getAccBalByCustId (Long customerId);

    public AccountDto getAccBalByAccNo(Long accNo);
}
