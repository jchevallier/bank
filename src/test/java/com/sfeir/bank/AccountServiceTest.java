package com.sfeir.bank;

import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class AccountServiceTest {

    private AccountService accountService = new AccountService();

    @AfterMethod
    public void tearDown() throws Exception {
        accountService.resetAccount();
    }

    @Test
    public void should_add_amount_to_balance_when_positive_amount() {
        BigDecimal amount = BigDecimal.TEN;

        OperationResult result = accountService.addAmount(amount);

        Assertions.assertThat(result.isValid()).isTrue();
        Assertions.assertThat(result.getResult()).isEqualTo(amount);
        Assertions.assertThat(accountService.getBalance()).isEqualTo(BigDecimal.TEN);
    }

    @Test
    public void should_not_remove_amount_when_insufficient_funds() {
        BigDecimal amount = BigDecimal.valueOf(-10);

        OperationResult result = accountService.addAmount(amount);

        Assertions.assertThat(result.isValid()).isFalse();
        Assertions.assertThat(result.getErrorType()).isEqualTo(ErrorType.INSUFFICIENT_FUNDS);
        Assertions.assertThat(accountService.getBalance()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void should_remove_amount_when_sufficient_funds() {
        accountService.setBalance(BigDecimal.valueOf(10));
        BigDecimal amount = BigDecimal.valueOf(-10);

        OperationResult result = accountService.addAmount(amount);

        Assertions.assertThat(result.isValid()).isTrue();
        Assertions.assertThat(result.getResult()).isEqualTo(amount);
        Assertions.assertThat(accountService.getBalance()).isEqualTo(BigDecimal.ZERO);
    }
}