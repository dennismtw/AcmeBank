package com.acmebank.banking.account.mapper;

import com.acmebank.banking.account.dto.AccountDto;
import com.acmebank.banking.account.entity.Account;
import org.springframework.beans.BeanUtils;

public class AccountMapper {

    public static AccountDto mapToAccountsDto(Account account, AccountDto accountDto) {
        BeanUtils.copyProperties(account,accountDto );
        return accountDto;
    }

    public static Account mapToAccounts(AccountDto accountsDto, Account account) {
        BeanUtils.copyProperties(accountsDto,account);
        return account;
    }

}
