package com.sfeir.bank.services;

import com.sfeir.bank.services.AccountService;
import org.assertj.core.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.stream.Stream;

public class ConcurrentAccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private DetailService detailService;

    public ConcurrentAccountServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        accountService.resetAccount();
    }

    @Test
    public void should_add_amount_to_balance_atomically_when_executed_in_concurrence() throws InterruptedException {
        BigDecimal amount = BigDecimal.TEN;

        Thread t1 = new Thread(() -> Stream.generate(() -> amount).limit(10).forEach(accountService::addAmount));
        Thread t2 = new Thread(() -> Stream.generate(() -> amount).limit(10).forEach(accountService::addAmount));
        t1.start();
        t2.start();

        t1.join();
        t2.join();
        Assertions.assertThat(accountService.getBalance()).isEqualTo(BigDecimal.valueOf(200));
    }

}