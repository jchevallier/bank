package com.sfeir.bank.services;

import org.assertj.core.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.stream.Stream;

public class ConcurrentDetailServiceTest {

    private static final Date NOW = new Date();

    @InjectMocks
    private DetailService detailService;

    @Mock
    private TimeService timeService;

    public ConcurrentDetailServiceTest() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(timeService.now()).thenReturn(NOW);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        detailService.resetOperations();
    }

    @Test
    public void should_add_amount_to_balance_atomically_when_executed_in_concurrence() throws InterruptedException {
        BigDecimal amount = BigDecimal.TEN;

        Thread t1 = new Thread(() -> Stream.generate(() -> amount).limit(100).forEach(detailService::addOperation));
        Thread t2 = new Thread(() -> Stream.generate(() -> amount).limit(100).forEach(detailService::addOperation));
        t1.start();
        t2.start();

        t1.join();
        t2.join();
        Assertions.assertThat(detailService.getOperations()).hasSize(200);
    }
}