package com.sfeir.bank.services;

import com.sfeir.bank.beans.ErrorType;
import com.sfeir.bank.beans.OperationResult;
import org.assertj.core.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private DetailService detailService;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        accountService.resetAccount();
    }

    @Test
    public void should_create_deposite_operation_and_update_balance_when_positive_amount() {
        BigDecimal amount = BigDecimal.TEN;

        accountService.addAmount(amount);

        Assertions.assertThat(accountService.getBalance()).isEqualTo(BigDecimal.TEN);
        Mockito.verify(detailService).addOperation(amount);
    }

    @Test
    public void should_not_remove_amount_when_insufficient_funds() {
        BigDecimal amount = BigDecimal.valueOf(-10);

        OperationResult result = accountService.addAmount(amount);

        Assertions.assertThat(result.isValid()).isFalse();
        Assertions.assertThat(result.getErrorType()).isEqualTo(ErrorType.INSUFFICIENT_FUNDS);
        Assertions.assertThat(accountService.getBalance()).isEqualTo(BigDecimal.ZERO);
        Mockito.verify(detailService, Mockito.never()).addOperation(amount);
    }

    @Test
    public void should_remove_amount_when_sufficient_funds() {
        accountService.setBalance(BigDecimal.valueOf(10));
        BigDecimal amount = BigDecimal.valueOf(-10);

        accountService.addAmount(amount);

        Assertions.assertThat(accountService.getBalance()).isEqualTo(BigDecimal.ZERO);
        Mockito.verify(detailService).addOperation(amount);
    }
}