package com.acmebank.banking.account;

import com.acmebank.banking.account.entity.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class AccountApplicationTests {

	@Test
	public void testRetrieveBalance() {
	//	when(accRepo.findByAccountId(1L)).thenReturn(Optional.of(new Account(1L, BigDecimal.ONE)));

		//assertEquals(BigDecimal.ONE, accService.retrieveBalances(1L).getBalance());
	}

}
