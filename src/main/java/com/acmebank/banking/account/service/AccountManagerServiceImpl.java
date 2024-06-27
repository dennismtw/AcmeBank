package com.acmebank.banking.account.service;

import com.acmebank.banking.account.constants.ErrorCode;
import com.acmebank.banking.account.dto.AccountDto;
import com.acmebank.banking.account.dto.TransactionDetailDto;
import com.acmebank.banking.account.dto.TransferRequestDto;
import com.acmebank.banking.account.entity.Account;
import com.acmebank.banking.account.entity.TransactionDetail;
import com.acmebank.banking.account.exception.AccountNotExistException;
import com.acmebank.banking.account.exception.CcyCodeNotMatchException;
import com.acmebank.banking.account.exception.OverDraftException;
import com.acmebank.banking.account.mapper.AccountMapper;
import com.acmebank.banking.account.mapper.TransactionDetailMapper;
import com.acmebank.banking.account.repository.AccountRepository;
import com.acmebank.banking.account.repository.TransactionDetailRepository;
import com.acmebank.banking.account.util.CodeNoUtil;
import com.acmebank.banking.account.util.CodePrefixCode;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AccountManagerServiceImpl implements AccountManagerService {

    private AccountRepository accountRepository;
    private TransactionDetailRepository transactionDetailRepository;

    @Transactional
    public TransactionDetailDto transfer (TransferRequestDto transferRequestDto) {

        Long frmAccNo = transferRequestDto.getFromAccNo();
        Long toAccNo = transferRequestDto.getToAccNo();
        BigDecimal transferAmt = transferRequestDto.getAmount();

        Account senderAcc = accountRepository.findById(frmAccNo).orElseThrow(() -> new AccountNotExistException("Sender Account not found in system:" + frmAccNo +
                " does not exist.", ErrorCode.ACCOUNT_ERROR));

        Account receiverAcc = accountRepository.findById(toAccNo).orElseThrow(() -> new AccountNotExistException("Receiver Account not found in system:" + frmAccNo +
                " does not exist.", ErrorCode.ACCOUNT_ERROR));

        BigDecimal senderCurrBalance = senderAcc.getAccBalance();
        BigDecimal receiverBalance = receiverAcc.getAccBalance();
        String senderAccCcyCode = senderAcc.getCurrencyCode();
        String receiverAccCcyCode = receiverAcc.getCurrencyCode();

        if(senderCurrBalance.compareTo(transferAmt) < 0) {
            throw new OverDraftException("Sender Account:" + frmAccNo + " does not have enough balance: " + transferAmt , ErrorCode.ACCOUNT_ERROR);
        }

        if(!senderAccCcyCode.equals(receiverAccCcyCode)) {
            throw new CcyCodeNotMatchException("sender account currency " + senderAccCcyCode + " does not match receiver account currency " + receiverAccCcyCode , ErrorCode.ACCOUNT_ERROR);
        }

        senderAcc.setAccBalance(senderCurrBalance.subtract(transferAmt));
        receiverAcc.setAccBalance(receiverBalance.add(transferAmt));

        accountRepository.save(senderAcc);
        accountRepository.save(receiverAcc);

        TransactionDetail txnDetail = new TransactionDetail();
        txnDetail.setTxnNo(CodeNoUtil.getNo(CodePrefixCode.CODE_TRANSACTION_PREFIX));
        txnDetail.setSenderAccNo(frmAccNo);
        txnDetail.setReceiverAccNo(toAccNo);
        txnDetail.setCurrencyCode(senderAcc.getCurrencyCode());
        txnDetail.setAmount(transferAmt);
        txnDetail.setTransferDt(LocalDateTime.now());
        transactionDetailRepository.save(txnDetail);
        TransactionDetailDto transactionDetailDto = TransactionDetailMapper.mapToDetailDto(txnDetail, new TransactionDetailDto());

        return transactionDetailDto;
    }

    public AccountDto getAccBalByCustId(Long customerId) {
        Account account = accountRepository.findByCustomerId(customerId).orElseThrow(() -> new AccountNotExistException("Account with customer id:" + customerId +
                " does not exist.", ErrorCode.ACCOUNT_ERROR, HttpStatus.NOT_FOUND));
        AccountDto accountDto = AccountMapper.mapToAccountDto(account, new AccountDto());
        return accountDto;
    }

    public AccountDto getAccBalByAccNo(Long accNo) {
        Account account = accountRepository.findById(accNo).orElseThrow(() -> new AccountNotExistException("Account with acc no:" + accNo +
                " does not exist.", ErrorCode.ACCOUNT_ERROR, HttpStatus.NOT_FOUND));
        AccountDto accountDto = AccountMapper.mapToAccountDto(account, new AccountDto());
        return accountDto;
    }

}
