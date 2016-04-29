package com.sfeir.bank.services;

import com.sfeir.bank.services.AccountService;
import com.sfeir.bank.services.AmountFormatter;
import com.sfeir.bank.services.OperationService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Optional;

public class OperationServiceTest {

    @InjectMocks
    private OperationService operationService = new OperationService();

    @Mock
    private AmountFormatter formatter;

    @Mock
    private AccountService accountService;

    private Optional<BigDecimal> formattedValue;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(formatter.format(Mockito.anyString())).then(invocationOnMock -> formattedValue);
    }

    @Test
    public void should_save_when_formatter_succeed() {
        formattedValue = Optional.of(BigDecimal.TEN);

        operationService.save("");

        Mockito.verify(accountService).addAmount(formattedValue.get());
    }

    @Test
    public void should_not_save_when_formatter_fail() {
        formattedValue = Optional.empty();

        operationService.save("");

        Mockito.verify(accountService, Mockito.never()).addAmount(Mockito.any());
    }

    @Test
    public void should_not_save_when_negative_amount() {
        formattedValue = Optional.of(BigDecimal.valueOf(-10));

        operationService.save("");

        Mockito.verify(accountService, Mockito.never()).addAmount(Mockito.any());
    }

    @Test
    public void should_not_save_when_amount_is_zero() {
        formattedValue = Optional.of(BigDecimal.ZERO);

        operationService.save("");

        Mockito.verify(accountService, Mockito.never()).addAmount(Mockito.any());
    }

}