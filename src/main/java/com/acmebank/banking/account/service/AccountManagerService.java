package com.acmebank.banking.account.service;

import com.acmebank.banking.account.dto.AccountDto;
import com.acmebank.banking.account.dto.TransferRequestDto;
import com.acmebank.banking.account.entity.Account;

import java.math.BigDecimal;

public interface AccountManagerService {

    public AccountDto transfer (TransferRequestDto transferRequestDto);

    public AccountDto getAccBalByCustId (Long customerId);

    public AccountDto getAccBalByAccNo(Long accNo);
}
