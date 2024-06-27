package com.acmebank.banking.account;

import com.acmebank.banking.account.dto.TransferRequestDto;
import com.acmebank.banking.account.entity.Account;
import com.acmebank.banking.account.exception.AccountNotExistException;
import com.acmebank.banking.account.exception.CcyCodeNotMatchException;
import com.acmebank.banking.account.exception.OverDraftException;
import com.acmebank.banking.account.repository.AccountRepository;
import com.acmebank.banking.account.repository.TransactionDetailRepository;
import com.acmebank.banking.account.service.AccountManagerServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class AccountApplicationTests {
	@Mock
	AccountRepository accountRepository;
	@Mock
	TransactionDetailRepository transactionDetailRepository;

	@InjectMocks
	AccountManagerServiceImpl accountManagerServiceImpl;

	Account testAcc1;
	Account testAcc2;
	Account testAcc3;

	@Before
	public void setup(){
		testAcc1 = new Account();
		testAcc1.setAccNo(12345678L);
		testAcc1.setCustomerId(1L);
		testAcc1.setAccBalance(BigDecimal.valueOf(100.000));
		testAcc1.setCurrencyCode("HKD");
		testAcc1.setAccType("SAVING");

		testAcc2 = new Account();
		testAcc2.setAccNo(88888888L);
		testAcc2.setCustomerId(2L);
		testAcc2.setAccBalance(BigDecimal.valueOf(200.000));
		testAcc2.setCurrencyCode("HKD");
		testAcc2.setAccType("CURRENT");

		testAcc3 = new Account();
		testAcc3.setAccNo(99999999L);
		testAcc3.setCustomerId(3L);
		testAcc3.setAccBalance(BigDecimal.valueOf(300.000));
		testAcc3.setCurrencyCode("USD");
		testAcc3.setAccType("CURRENT");

	}

	@Test
	public void testGetAccBalByCustId() {
		when(accountRepository.findByCustomerId(1L)).thenReturn(Optional.of(testAcc1));
		assertEquals(BigDecimal.valueOf(100.000), accountManagerServiceImpl.getAccBalByCustId(1L).getAccBalance());
		assertEquals("HKD", accountManagerServiceImpl.getAccBalByCustId(1L).getCurrencyCode());
	}

	@Test(expected = AccountNotExistException.class)
	public void testGetAccBalByCustIdNotExist() {
		assertEquals(BigDecimal.valueOf(100.000), accountManagerServiceImpl.getAccBalByCustId(5L).getAccBalance());
	}

	@Test
	public void testGetAccBalByAccNo() {
		when(accountRepository.findById(88888888L)).thenReturn(Optional.of(testAcc2));
		assertEquals(BigDecimal.valueOf(200.000), accountManagerServiceImpl.getAccBalByAccNo(88888888L).getAccBalance());
		assertEquals("HKD", accountManagerServiceImpl.getAccBalByAccNo(88888888L).getCurrencyCode());
	}

	@Test(expected = AccountNotExistException.class)
	public void testGetAccBalByAccNoNotExist() {
		assertEquals(BigDecimal.valueOf(200.000), accountManagerServiceImpl.getAccBalByAccNo(66666666L).getAccBalance());
	}


	// Test Money Transfer
	@Test
	public void testTransfer() {
		//Set From Account Balance for test
		testAcc1.setAccBalance(BigDecimal.valueOf(100.000));
		//Set To Account Balance for test
		testAcc2.setAccBalance(BigDecimal.valueOf(100.000));

		when(accountRepository.findById(12345678L)).thenReturn(Optional.of(testAcc1));
		when(accountRepository.findById(88888888L)).thenReturn(Optional.of(testAcc2));


		TransferRequestDto transferRequest = new TransferRequestDto();
		transferRequest.setFromAccNo(testAcc1.getAccNo());
		transferRequest.setToAccNo(testAcc2.getAccNo());
		transferRequest.setAmount(BigDecimal.valueOf(100.000));

		accountManagerServiceImpl.transfer(transferRequest);

		assertEquals(BigDecimal.valueOf(0.000), testAcc1.getAccBalance());
		assertEquals(BigDecimal.valueOf(200.000), testAcc2.getAccBalance());
	}

	// Test OverDraft Not Match
	@Test(expected = OverDraftException.class)
	public void testTransferOverdraft()	{
		//Set From Account Balance for test
		testAcc1.setAccBalance(BigDecimal.valueOf(0.000));
		//Set To Account Balance for test
		testAcc2.setAccBalance(BigDecimal.valueOf(100.000));

		when(accountRepository.findById(12345678L)).thenReturn(Optional.of(testAcc1));
		when(accountRepository.findById(88888888L)).thenReturn(Optional.of(testAcc2));

		TransferRequestDto transferRequest = new TransferRequestDto();
		transferRequest.setFromAccNo(testAcc1.getAccNo());
		transferRequest.setToAccNo(testAcc2.getAccNo());
		transferRequest.setAmount(BigDecimal.valueOf(100.000));

		accountManagerServiceImpl.transfer(transferRequest);
	}

	// Test Currency Not Match
	@Test(expected = CcyCodeNotMatchException.class)
	public void testTransferAccountCurrencyNotMatch()	{
		//Set From Account Balance for test
		testAcc1.setAccBalance(BigDecimal.valueOf(100.000));
		//Set To Account Balance for test
		testAcc3.setAccBalance(BigDecimal.valueOf(100.000));

		when(accountRepository.findById(12345678L)).thenReturn(Optional.of(testAcc1));
		when(accountRepository.findById(99999999L)).thenReturn(Optional.of(testAcc3));

		TransferRequestDto transferRequest = new TransferRequestDto();
		transferRequest.setFromAccNo(testAcc1.getAccNo());
		transferRequest.setToAccNo(testAcc3.getAccNo());
		transferRequest.setAmount(BigDecimal.valueOf(100.000));

		accountManagerServiceImpl.transfer(transferRequest);
	}

	// Test Account Not exist in system
	@Test(expected = AccountNotExistException.class)
	public void testTransferAccountNotExist()	{
		//Set From Account Balance for test
		testAcc1.setAccBalance(BigDecimal.valueOf(100.000));
		//Set To Account Balance for test
		testAcc2.setAccBalance(BigDecimal.valueOf(100.000));
		testAcc2.setAccNo(00000001L);

		when(accountRepository.findById(12345678L)).thenReturn(Optional.of(testAcc1));

		TransferRequestDto transferRequest = new TransferRequestDto();
		transferRequest.setFromAccNo(testAcc1.getAccNo());
		transferRequest.setToAccNo(testAcc2.getAccNo());
		transferRequest.setAmount(BigDecimal.valueOf(100.000));

		accountManagerServiceImpl.transfer(transferRequest);
	}
}
