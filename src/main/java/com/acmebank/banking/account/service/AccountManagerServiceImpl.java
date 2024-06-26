package com.acmebank.banking.account.service;

import com.acmebank.banking.account.constants.ErrorCode;
import com.acmebank.banking.account.constants.ErrorMessage;
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
import java.text.MessageFormat;
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

        Account senderAcc = accountRepository.findById(frmAccNo).orElseThrow(() -> new AccountNotExistException(
                MessageFormat.format(ErrorMessage.SENDER_ACC_NO_NOT_EXIST, frmAccNo), ErrorCode.ACCOUNT_ERROR));
        Account receiverAcc = accountRepository.findById(toAccNo).orElseThrow(() -> new AccountNotExistException(
                MessageFormat.format(ErrorMessage.RECEIVER_ACC_NO_NOT_EXIST, toAccNo), ErrorCode.ACCOUNT_ERROR));

        BigDecimal senderCurrBalance = senderAcc.getAccBalance();
        BigDecimal receiverBalance = receiverAcc.getAccBalance();
        String senderAccCcyCode = senderAcc.getCurrencyCode();
        String receiverAccCcyCode = receiverAcc.getCurrencyCode();

        if(senderCurrBalance.compareTo(transferAmt) < 0) {
            throw new OverDraftException(MessageFormat.format(ErrorMessage.SENDER_NOT_ENOUGH_BALANCE, frmAccNo, transferAmt) , ErrorCode.ACCOUNT_ERROR);
        }
        if(!senderAccCcyCode.equals(receiverAccCcyCode)) {
            throw new CcyCodeNotMatchException(MessageFormat.format(ErrorMessage.SENDER_RECEIVER_CURRENCY_NOT_MATCH, senderAccCcyCode,receiverAccCcyCode)
            , ErrorCode.ACCOUNT_ERROR);
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

        return TransactionDetailMapper.mapToDetailDto(txnDetail, new TransactionDetailDto());
    }

    public AccountDto getAccBalByCustId(Long customerId) {
        Account account = accountRepository.findByCustomerId(customerId).orElseThrow(() -> new AccountNotExistException(MessageFormat.format(ErrorMessage.ACCT_WITH_CUST_ID_NOT_EXIST, customerId), ErrorCode.ACCOUNT_ERROR, HttpStatus.NOT_FOUND));
        return AccountMapper.mapToAccountDto(account, new AccountDto());
    }

    public AccountDto getAccBalByAccNo(Long accNo) {
        Account account = accountRepository.findById(accNo).orElseThrow(() -> new AccountNotExistException(MessageFormat.format(ErrorMessage.ACCT_WITH_ACC_NO_NOT_EXIST, accNo), ErrorCode.ACCOUNT_ERROR, HttpStatus.NOT_FOUND));
        return AccountMapper.mapToAccountDto(account, new AccountDto());
    }

}
