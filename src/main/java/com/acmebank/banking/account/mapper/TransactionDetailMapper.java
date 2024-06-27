package com.acmebank.banking.account.mapper;

import com.acmebank.banking.account.dto.TransactionDetailDto;
import com.acmebank.banking.account.entity.TransactionDetail;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

public class TransactionDetailMapper {
    public static TransactionDetailDto mapToDetailDto(TransactionDetail txnDtl, TransactionDetailDto txnDtlDto) {
        BeanUtils.copyProperties(txnDtl,txnDtlDto );
        txnDtlDto.setAmount(txnDtl.getAmount().setScale(3));
        return txnDtlDto;
    }

    public static TransactionDetail mapToDetail(TransactionDetailDto txnDtlDto , TransactionDetail txnDtl) {
        BeanUtils.copyProperties(txnDtlDto,txnDtl);
        return txnDtl;
    }
}
