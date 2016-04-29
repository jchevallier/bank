package com.sfeir.bank;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.stream.Stream;

public class ConcurrentAccountServiceTest {

    private AccountService accountService = new AccountService();

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